package com.example.particlelife;

public class QuadTree {

    double center_of_mass_x;
    double center_of_mass_y;
    double total_mass;
    double num_particles;
    double distance_x_sum;
    double distance_y_sum;

    float theta_threshold;
    float particle_mass;
    int softening_factor;
    float max_speed;

    boolean split;
    double x0;
    double x1;
    double y0;
    double y1;

    double SCREEN_WIDTH;
    double SCREEN_HEIGHT;

    Particle cell;

    QuadTree upper;
    QuadTree top_left;
    QuadTree top_right;
    QuadTree bottom_left;
    QuadTree bottom_right;

    public QuadTree(double x0, double y0, double x1, double y1, QuadTree upper, float gravity_strength, float max_speed, float theta, int soft_power) {
        this.x0 = x0;
        this.x1 = x1;
        this.y0 = y0;
        this.y1 = y1;

        this.upper = upper;
        if (this.upper == null) {
            this.SCREEN_WIDTH = x1;
            this.SCREEN_HEIGHT = y1;
        } else {
            this.SCREEN_WIDTH = this.upper.SCREEN_WIDTH;
            this.SCREEN_HEIGHT = this.upper.SCREEN_HEIGHT;
        }

        // tunable
        // ========
        softening_factor = soft_power;
        theta_threshold = theta;
        particle_mass = gravity_strength;
        this.max_speed = max_speed;
        // ========

        center_of_mass_x = 0;
        center_of_mass_y = 0;
        total_mass = 0;
        num_particles = 0;
        distance_x_sum = 0;
        distance_y_sum = 0;

        top_left = null;
        top_right = null;
        bottom_left = null;
        bottom_right = null;

        cell = null;
        split = false;

    }

    public void calculate_gravity(Particle p, double other_x, double other_y, double mass, double dt) {

        double dx = (other_x - p.getCenterX());
        double dy = (other_y - p.getCenterY());
        if (dx == 0 && dy == 0) {
            return;
        }

//        // TODO: scale point mass for realism
//        double radius_squared = dx*dx + dy*dy;
//        double softening = Math.pow(10, softening_factor);
//        double a_mag = mass/(radius_squared + softening*softening);
//        double angle = atan2(dy, dx);
//        p->ax += a_mag*cos(angle);
//        p->ay += a_mag*sin(angle);
    }


    public void update_point_gravity(Particle p, double dt) {
        if (cell != null & p != null) {
            calculate_gravity(p, cell.getCenterX(), cell.getCenterY(), particle_mass, dt);
        }
        else{
            double s = x1-x0;
            assert p != null;
            double d = distance(p.getCenterX(), p.getCenterY(), center_of_mass_x, center_of_mass_y);
            if (d<=0 || Double.isNaN(d)) {
                return;
            }
            if (((double)s/d > theta_threshold) & split) {
                top_left.update_point_gravity(p, dt);
                top_right.update_point_gravity(p, dt);
                bottom_left.update_point_gravity(p, dt);
                bottom_right.update_point_gravity(p, dt);
            }
            else {
                calculate_gravity(p, center_of_mass_x, center_of_mass_y, particle_mass*num_particles, dt);
            }

        }
    }

    private double distance(double x0, double y0, double x1, double y1) {
        return Math.sqrt(Math.pow(x0-x1, 2) + Math.pow(y0-y1, 2));
    }

    public void update_life(QuadTree root, double dt) {
        if (cell != null) { //only check leaf nodes
            cell.setAcceleration(0, 0);
            root.update_point_gravity(cell, dt);
            cell.update(dt);
        }
        else if (split){
            top_left.update_life(root, dt);
            top_right.update_life(root, dt);
            bottom_left.update_life(root, dt);
            bottom_right.update_life(root, dt);
        }
        return;

    }
    public boolean insert(Particle p) {
        // verify Particle before inserting
        if (p.getCenterX() < x0 || p.getCenterX() > x1 || p.getCenterY() < y0 || p.getCenterY() > y1) {
            return false;
        }

        num_particles++;
        distance_x_sum += p.getCenterX();
        distance_y_sum += p.getCenterY();
        center_of_mass_x = distance_x_sum / (num_particles);
        center_of_mass_y = distance_y_sum / (num_particles);

        double x_mid = (double)(x1 + x0)/2.0;
        double y_mid = (double)(y1 + y0)/2.0;

        if (!split & cell == null) {
            cell = p;
            return true;
        }

        if (split) { // case when array if full but have not split
            // recursive insert!
            // search and insert at leaf node
            if (p.getCenterX() <= x_mid & p.getCenterY() <= y_mid) {
                return top_left.insert(p);
            } else if (p.getCenterX() <= x1 & p.getCenterY() <= y_mid) {
                return top_right.insert(p);
            } else if (p.getCenterX() <= x_mid & p.getCenterY() <= y1) {
                return bottom_left.insert(p);
            } else if (p.getCenterX() <= x1 & p.getCenterY() <= y1) {
                return bottom_right.insert(p);
            }
        } else {
            //split
            top_left = new QuadTree(x0, y0, x_mid, y_mid, this, particle_mass, max_speed, theta_threshold, softening_factor);
            top_right = new QuadTree(x_mid, y0, x1, y_mid, this, particle_mass, max_speed, theta_threshold, softening_factor);
            bottom_left = new QuadTree(x0, y_mid, x_mid, y1, this, particle_mass, max_speed, theta_threshold, softening_factor);
            bottom_right = new QuadTree(x_mid, y_mid, x1, y1, this, particle_mass, max_speed, theta_threshold, softening_factor);

            // copies existing Particles to child quadrants
            split = true;

            num_particles--;
            distance_x_sum -= cell.getCenterX();
            distance_y_sum -= cell.getCenterY();

            center_of_mass_x = distance_x_sum / (num_particles);
            center_of_mass_y = distance_y_sum / (num_particles);

            insert(cell);
            cell = null;
            return insert(p);


        }
        return false;
    }
}
