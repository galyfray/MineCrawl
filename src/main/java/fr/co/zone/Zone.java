package fr.co.zone;

import org.bukkit.Location;

public class Zone {

    private Location cooNeg;
    private Location cooPos;
    private String name;

    public Zone(Location cooNeg,Location cooPos,String name){
        this.cooNeg = cooNeg;
        this.cooPos = cooPos;
        this.name = name;
    }

    public boolean isInZone(Location loc){
        /**
         * @brief Cette fonction permet de savoir si un objet de type location est dans la Zonne en
         * testant si le x de cette location est entre le x de la coordonée négative de la zonne et la coordonée positive
         * de même pour les y,z.
         *
         * @param loc la localisation qui est tester
         * @return True si la Location est dans la zonne False sinon.
         */
        if (loc.getWorld().equals(cooNeg.getWorld())){
            if(loc.getBlockX()>=cooNeg.getBlockX() && loc.getBlockX() <= cooPos.getBlockX()){
                if(loc.getBlockY()>=cooNeg.getBlockY() && loc.getBlockY() <= cooPos.getBlockY()){
                    if(loc.getBlockZ()>=cooNeg.getBlockZ() && loc.getBlockZ() <= cooPos.getBlockZ()){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public Location getCooNeg() {
        return cooNeg;
    }

    public Location getCooPos() {
        return cooPos;
    }

}