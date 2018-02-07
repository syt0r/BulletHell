package ua.syt0r.actors.entities;

public class Boss extends Enemy{

    private int health;
    float time = 0f;

    public Boss(int health){

        super();

    }

    @Override
    public void update(float delta) {

        time += delta;



    }
}
