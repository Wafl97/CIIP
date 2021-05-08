package org.example.logic;

public class Domain implements Logic{

    private static Domain instance;

    public static Domain getInstance(){
        return instance == null ? instance = new Domain() : instance;
    }

    @Override
    public void createContainer(Container container) {
        System.out.println("CREATE NOT YET IMPLEMENTED");
        System.out.println(container.getClass());
    }

    @Override
    public Container readContainer(Container container) {
        Container newContainer;
        if (container instanceof Capsule) newContainer = new Capsule();
        else newContainer = new SouvenirCase();
        System.out.println("READ NOT YET IMPLEMENTED");
        System.out.println(newContainer.getClass());
        return newContainer;
    }

    @Override
    public void updateContainer(Container container) {
        System.out.println("UPDATE NOT YET IMPLEMENTED");
        System.out.println(container.getClass());
    }

    @Override
    public void deleteContainer(Container container) {
        System.out.println("DELETE NOT YET IMPLEMENTED");
        System.out.println(container.getClass());
    }
}
