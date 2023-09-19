package edu.school21.classes;

import edu.school21.annotations.OrmColumn;
import edu.school21.annotations.OrmColumnId;
import edu.school21.annotations.OrmEntity;
import org.reflections.Reflections;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class OrmManager {

    private final DataSource dataSource;

    public OrmManager(DataSource dataSource) {
        this.dataSource = dataSource;
        createTables();
    }

    private void createTables() {
        Reflections reflections = new Reflections("edu.school21.models");
        Set<Class<?>> classSet = reflections.getTypesAnnotatedWith(OrmEntity.class);
        for (Class<?> clas : classSet) {
            String dropQuery = String.format("DROP TABLE IF EXISTS %s CASCADE",
                    clas.getAnnotation(OrmEntity.class).table());
            String createQuery = createTableQuery(clas);
            if (createQuery == null) {
                continue;
            }
            try (Connection connection = dataSource.getConnection();
                 Statement statement = connection.createStatement()) {
                statement.execute(dropQuery);
                statement.execute(createQuery);
                System.out.println(dropQuery);
                System.out.println(createQuery);
            } catch (SQLException e) {
                System.err.println("An error in Create Tables method of ORM Manager!");
            }
        }
    }

    private String createTableQuery(Class<?> clas) {
        OrmEntity ormEntity = clas.getAnnotation(OrmEntity.class);
        StringBuilder query = new StringBuilder("CREATE TABLE ");
        query.append(ormEntity.table()).append(" (");
        Field[] fields = clas.getDeclaredFields();
        if (Arrays.stream(fields)
                .noneMatch(f -> f.isAnnotationPresent(OrmColumn.class) ||
                        f.isAnnotationPresent(OrmColumnId.class))) {
            System.err.printf("The class %s does not have annotated fields!\n", clas.getSimpleName());
            return null;
        }
        for (Field field : fields) {
            if (field.isAnnotationPresent(OrmColumnId.class)) {
                String idType = field.getType().getSimpleName();
                if (idType.equalsIgnoreCase("Integer") ||
                        idType.equalsIgnoreCase("Long")) {
                    query.append(field.getName()).append(" BIGINT GENERATED ALWAYS AS IDENTITY, ");
                } else {
                    System.err.println("The id should be of type Integer or Long!");
                    return null;
                }
            }
            if (field.isAnnotationPresent(OrmColumn.class)) {
                OrmColumn ormColumn = field.getAnnotation(OrmColumn.class);
                query.append(ormColumn.name());
                query.append(getSQLType(field.getType().getSimpleName(), ormColumn.length()));
            }
        }
        return query.replace(query.lastIndexOf(","), query.length(), ")").toString();
    }

    private String getSQLType(String name, int length) {
        String sqlType = "";
        if (name.equalsIgnoreCase("Integer")) {
            sqlType = " INT, ";
        } else if (name.equalsIgnoreCase("String")) {
            sqlType = String.format(" VARCHAR(%d), ", length);
        } else if (name.equalsIgnoreCase("Boolean")) {
            sqlType = " BOOLEAN, ";
        } else if (name.equalsIgnoreCase("Double")) {
            sqlType = " DOUBLE PRECISION, ";
        } else if (name.equalsIgnoreCase("Long")) {
            sqlType = " BIGINT, ";
        }
        return sqlType;
    }

    public void save(Object entity) {
        if (!entity.getClass().isAnnotationPresent(OrmEntity.class)) {
            return;
        }
        String saveQuery = createSaveQuery(entity);
        if (saveQuery == null) {
            return;
        }
        List<Field> idFieldList = Arrays.stream(entity.getClass().getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(OrmColumnId.class)).collect(Collectors.toList());
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(saveQuery, Statement.RETURN_GENERATED_KEYS);
            System.out.println(saveQuery);
            if (idFieldList.size() > 0) {
                ResultSet keys = statement.getGeneratedKeys();
                for (int i = 0; i < idFieldList.size() && keys.next(); i++) {
                    Field field = idFieldList.get(i);
                    field.setAccessible(true);
                    field.set(entity, keys.getLong(field.getName()));
                }
            }
        } catch (SQLException e) {
            System.err.println("An error in Saving method of ORM Manager!");
        } catch (IllegalAccessException e) {
            System.err.println("Could not get access to the id field!");
        }
    }

    private String createSaveQuery(Object entity) {
        StringBuilder insertPart = new StringBuilder("INSERT INTO ");
        StringBuilder valuesPart = new StringBuilder(" VALUES (");
        Class<?> clas = entity.getClass();
        insertPart.append(clas.getAnnotation(OrmEntity.class).table()).append(" (");
        Field[] fields = clas.getDeclaredFields();
        if (Arrays.stream(fields)
                .noneMatch(f -> f.isAnnotationPresent(OrmColumn.class) ||
                        f.isAnnotationPresent(OrmColumnId.class))) {
            System.err.printf("The class %s does not have annotated fields!\n", clas.getSimpleName());
            return null;
        }
        for (Field field : fields) {
            if (field.isAnnotationPresent(OrmColumn.class)) {
                insertPart.append(field.getAnnotation(OrmColumn.class).name()).append(", ");
                field.setAccessible(true);
                try {
                    if (field.getType().getSimpleName().equalsIgnoreCase("String")) {
                        valuesPart.append(String.format("'%s', ", field.get(entity)));
                    } else {
                        valuesPart.append(field.get(entity)).append(", ");
                    }
                } catch (IllegalAccessException e) {
                    System.err.println("Could not get a value to save entity!");
                    return null;
                }
            }
        }
        insertPart.replace(insertPart.lastIndexOf(","), insertPart.length(), ")");
        valuesPart.replace(valuesPart.lastIndexOf(", "), valuesPart.length(), ")");
        return insertPart.append(valuesPart).toString();
    }

    public void update(Object entity) {
        if (!entity.getClass().isAnnotationPresent(OrmEntity.class)) {
            return;
        }
        String updateQuery = createUpdateQuery(entity);
        if (updateQuery == null) {
            return;
        }
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(updateQuery);
            System.out.println(updateQuery);
        } catch (SQLException e) {
            System.err.println("An error in Updating method of ORM Manager!");
        }
    }

    private String createUpdateQuery(Object entity) {
        StringBuilder updatePart = new StringBuilder("UPDATE ");
        StringBuilder wherePart = new StringBuilder(" WHERE ");
        Class<?> clas = entity.getClass();
        updatePart.append(clas.getAnnotation(OrmEntity.class).table()).append(" SET ");
        Field[] fields = clas.getDeclaredFields();
        if (Arrays.stream(fields)
                .noneMatch(f -> f.isAnnotationPresent(OrmColumn.class) ||
                        f.isAnnotationPresent(OrmColumnId.class))) {
            System.err.printf("The class %s does not have annotated fields!\n", clas.getSimpleName());
            return null;
        }
        for (Field field : fields) {
            if (field.isAnnotationPresent(OrmColumnId.class)) {
                wherePart.append(field.getName()).append("=");
                field.setAccessible(true);
                try {
                    wherePart.append(field.get(entity)).append(" AND ");
                } catch (IllegalAccessException e) {
                    System.err.println("Could not get a value of ID Column to save entity!");
                    return null;
                }
            }
            if (field.isAnnotationPresent(OrmColumn.class)) {
                updatePart.append(field.getAnnotation(OrmColumn.class).name()).append("=");
                field.setAccessible(true);
                try {
                    if (field.getType().getSimpleName().equalsIgnoreCase("String")) {
                        updatePart.append(String.format("'%s', ", field.get(entity)));
                    } else {
                        updatePart.append(field.get(entity)).append(", ");
                    }
                } catch (IllegalAccessException e) {
                    System.err.println("Could not get a value of Column to save entity!");
                    return null;
                }
            }
        }
        updatePart.delete(updatePart.lastIndexOf(","), updatePart.length());
        wherePart.delete(wherePart.lastIndexOf("A") - 1, wherePart.length());
        return updatePart.append(wherePart).toString();
    }

    public <T> T findById(Long id, Class<T> aClass) {
        if (!aClass.isAnnotationPresent(OrmEntity.class)) {
            return null;
        }
        Field idField = Arrays.stream(aClass.getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(OrmColumnId.class) &&
                        f.getName().equalsIgnoreCase("id"))
                .collect(Collectors.toList()).get(0);
        String query = String.format("SELECT * FROM %s WHERE %s = %d",
                aClass.getAnnotation(OrmEntity.class).table(), idField.getName(), id);
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(query);
            System.out.println(query);
            ResultSet resultSet = statement.getResultSet();
            if (resultSet.next()) {
                return getEntityFromResultSet(resultSet, aClass);
            } else {
                System.err.println();
            }
        } catch (SQLException e) {
            System.err.println("An error in Find By Id method of ORM Manager!");
        } catch (InstantiationException e) {
            System.err.println("InstantiationException in getEntityFromResultSet");
        } catch (IllegalAccessException e) {
            System.err.println("IllegalAccessException in getEntityFromResultSet");
        }
        return null;
    }

    private static <T> T getEntityFromResultSet(ResultSet resultSet, Class<T> aClass) throws InstantiationException, IllegalAccessException, SQLException {
        T entity = aClass.newInstance();
        Field[] fields = aClass.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(OrmColumnId.class)) {
                field.setAccessible(true);
                field.set(entity, resultSet.getLong(field.getName()));
            }
            if (field.isAnnotationPresent(OrmColumn.class)) {
                field.setAccessible(true);
                field.set(entity, resultSet.getObject(field.getAnnotation(OrmColumn.class).name()));
            }
        }
        return entity;
    }
}
