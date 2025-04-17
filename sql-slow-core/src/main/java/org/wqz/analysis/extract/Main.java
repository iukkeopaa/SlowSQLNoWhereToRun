package org.wqz.analysis.extract;

class User {
    private String name;
    private int age;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

class MetaObject {
    private Object object;
    private ObjectFactory objectFactory;
    private ObjectWrapperFactory objectWrapperFactory;
    private ReflectorFactory reflectorFactory;

    public MetaObject(Object object, ObjectFactory objectFactory, ObjectWrapperFactory objectWrapperFactory, ReflectorFactory reflectorFactory) {
        this.object = object;
        this.objectFactory = objectFactory;
        this.objectWrapperFactory = objectWrapperFactory;
        this.reflectorFactory = reflectorFactory;
    }

    public static MetaObject forObject(Object object, ObjectFactory objectFactory, ObjectWrapperFactory objectWrapperFactory, ReflectorFactory reflectorFactory) {
        return new MetaObject(object, objectFactory, objectWrapperFactory, reflectorFactory);
    }

    // 模拟获取属性值的方法
    public Object getValue(String propertyName) {
        try {
            java.lang.reflect.Method method = object.getClass().getMethod("get" + Character.toUpperCase(propertyName.charAt(0)) + propertyName.substring(1));
            return method.invoke(object);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 模拟设置属性值的方法
    public void setValue(String propertyName, Object value) {
        try {
            java.lang.reflect.Method method = object.getClass().getMethod("set" + Character.toUpperCase(propertyName.charAt(0)) + propertyName.substring(1), value.getClass());
            method.invoke(object, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class ObjectFactory {
    // 空实现，这里仅作占位
}

class ObjectWrapperFactory {
    // 空实现，这里仅作占位
}

class ReflectorFactory {
    // 空实现，这里仅作占位
}

class MetaObjectUtil {

    private static final ObjectFactory DEFAULT_OBJECT_FACTORY = new ObjectFactory();
    private static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new ObjectWrapperFactory();
    private static final ReflectorFactory DEFAULT_REFLECTOR_FACTORY = new ReflectorFactory();

    /**
     * MetaObject 对象实例化
     * @param object
     * @return
     */
    public static MetaObject forObject(Object object) {
        return MetaObject.forObject(object, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY, DEFAULT_REFLECTOR_FACTORY);
    }
}

public class Main {
    public static void main(String[] args) {
        User user = new User("John", 30);
        MetaObject metaObject = MetaObjectUtil.forObject(user);

        // 访问属性
        String name = (String) metaObject.getValue("name");
        int age = (int) metaObject.getValue("age");
        System.out.println("Original name: " + name);
        System.out.println("Original age: " + age);

        // 修改属性
        metaObject.setValue("name", "Alice");
        metaObject.setValue("age", 25);

        // 再次访问属性
        name = (String) metaObject.getValue("name");
        age = (int) metaObject.getValue("age");
        System.out.println("Modified name: " + name);
        System.out.println("Modified age: " + age);
    }
}