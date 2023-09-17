package edu.school21.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Collectors;

public class Program {

    private static final String PATH = "edu.school21.classes";

    private static final String DELIMITER = "---------------------";

    private static Object object;

    private static Class<?> clas;

    private static List<Class<?>> allClasses;

    private static Method[] methods;

    private static Scanner scanner;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        getAllClasses();
        printClasses();
        getChosenClass();
        printClassInformation();
        createAnObject();
        changeField();
        invokeMethod();
        scanner.close();
    }

    private static void getAllClasses() {
        try (InputStream stream =  ClassLoader.getSystemClassLoader().getResourceAsStream(PATH.replaceAll("\\.", "/"));
             BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
            allClasses = reader.lines()
                    .filter(line -> line.endsWith(".class"))
                    .map(Program::getClassFromPath)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println("IOException in getAllClasses method!");
            System.exit(1);
        }
    }

    private static Class<?> getClassFromPath(String line) {
        try {
            return Class.forName(PATH + "." + line.substring(0, line.lastIndexOf('.')));
        } catch (ClassNotFoundException e) {
            System.out.println("IOException in getClassFromPath method!");
            System.exit(1);
        }
        return null;
    }

    private static void printClasses() {
        System.out.println("Classes:");
        for (Class<?> c : allClasses) {
            System.out.println("  - " + c.getSimpleName());
        }
        System.out.println(DELIMITER);
    }

    private static void getChosenClass() {
        boolean isFound = false;
        while (!isFound) {
            System.out.println("Enter class name:");
            String line = scanner.nextLine();
            for (Class<?> s : allClasses) {
                if (line.equalsIgnoreCase(s.getSimpleName())) {
                    clas = s;
                    isFound = true;
                    break;
                }
            }
        }
        System.out.println(DELIMITER);
    }

    private static void printClassInformation() {
        Field[] fields = clas.getDeclaredFields();
        methods = clas.getDeclaredMethods();

        System.out.println("fields:");
        for (Field f : fields) {
            System.out.println("\t" + f.getType().getSimpleName() + " " + f.getName());
        }

        System.out.println("methods:");
        for (Method m : methods) {
            if (m.getName().equals("toString")) {
                continue;
            }
            StringBuilder typesBuilder = new StringBuilder("(");
            Type[] types = m.getParameterTypes();
            for (Type t : types) {
                String name = t.getTypeName();
                if (name.contains(".")) {
                    name = name.substring(name.lastIndexOf(".") + 1);
                }
                typesBuilder.append(name);
                typesBuilder.append(", ");
            }
            if (typesBuilder.length() > 2) {
                typesBuilder.replace(typesBuilder.length() - 2, typesBuilder.length(), ")");
            } else {
                typesBuilder.append(")");
            }
            System.out.println("\t" + m.getReturnType().getSimpleName() + " " + m.getName() + typesBuilder);
        }
        System.out.println(DELIMITER);
    }

    private static void createAnObject() {
        System.out.println("Let’s create an object.");
        Constructor<?> constructor = Arrays.stream(clas.getConstructors())
                .filter(c -> c.getParameterCount() > 0)
                .collect(Collectors.toList()).get(0);
        Parameter[] parameters = constructor.getParameters();
        List<Object> args = new ArrayList<>();
        for (Parameter p : parameters) {
            args.add(getParameter(p.getName(), p.getType().getSimpleName().toLowerCase()));
        }
        try {
            object = constructor.newInstance(args.toArray());
        } catch (ReflectiveOperationException e) {
            System.err.println("ReflectiveOperationException in createAnObject method!");
            System.exit(3);
        }
        System.out.println("Object created: " + object);
        System.out.println(DELIMITER);
    }

    private static Object getParameter(String name, String type) {
        Object next = null;
        while (next == null) {
            System.out.println(name + ":");
            String nextLine = scanner.nextLine();
            try {
                switch (type) {
                    case "string":
                        next = nextLine;
                        break;
                    case "int":
                    case "integer":
                        next = Integer.parseInt(nextLine);
                        break;
                    case "short":
                        next = Short.parseShort(nextLine);
                        break;
                    case "long":
                        next = Long.parseLong(nextLine);
                        break;
                    case "byte":
                        next = Byte.parseByte(nextLine);
                        break;
                    case "float":
                        next = Float.parseFloat(nextLine);
                        break;
                    case "double":
                        next = Double.parseDouble(nextLine);
                        break;
                    case "char":
                    case "character":
                        next = nextLine.charAt(0);
                        break;
                    case "boolean":
                        next = Boolean.parseBoolean(nextLine);
                        break;
                    default:
                        System.err.println("The parameter type is not primitive");
                        System.exit(2);
                }
            } catch (NumberFormatException e) {
                System.err.println("Error in type of input");
            }
        }
        return next;
    }

    private static void changeField() {
        System.out.println("Enter name of the field for changing:");
        Field field = null;
        while (field == null) {
            try {
                field = object.getClass().getDeclaredField(scanner.nextLine());
            } catch (NoSuchFieldException e) {
                System.err.println("No such field in the class!");
            }
        }
        field.setAccessible(true);
        try {
            field.set(object, getParameter("Enter " + field.getType().getSimpleName() + " value",
                    field.getType().getSimpleName().toLowerCase()));
        } catch (IllegalAccessException e) {
            System.err.println("Error in changing a field!");
            System.exit(4);
        }
        System.out.println("Object updated: " + object);
        System.out.println(DELIMITER);
    }

    private static void invokeMethod() {
        Method method = null;
        while (method == null) {
            System.out.println("Enter name of the method for call:");
            String methodName = scanner.nextLine();
            if (methodName.contains("(")) {
                methodName = methodName.substring(0, methodName.indexOf("("));
            }
            for (Method m : methods) {
                if (methodName.equalsIgnoreCase(m.getName())) {
                    method = m;
                    break;
                }
            }
            if (method == null) {
                System.err.println("The method was not found!");
            }
        }
        Class<?>[] parameterTypes = method.getParameterTypes();
        List<Object> parameters = new ArrayList<>();
        for (Class<?> c : parameterTypes) {
            parameters.add(getParameter("Enter " + c.getSimpleName() + " value",
                    c.getSimpleName().toLowerCase()));
        }
        try {
            Object retObject = method.invoke(object, parameters.toArray());
            if (retObject != null) {
                System.out.println("Method returned:");
                System.out.println(retObject);
            }
        } catch (ReflectiveOperationException e) {
            System.err.println("Error in invoking the method!");
            System.exit(5);
        }
    }
}
