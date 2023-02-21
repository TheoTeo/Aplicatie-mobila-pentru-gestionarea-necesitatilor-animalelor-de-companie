package com.ase.dam.licenta01;

public class PlimbareMasaAlarma {
    private String idUser;
    private long ora;
    private long minut;

    public PlimbareMasaAlarma(String idUser, long ora, long minut) {
        this.idUser = idUser;
        this.ora = ora;
        this.minut = minut;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public PlimbareMasaAlarma(long ora, long minut) {
        this.ora = ora;
        this.minut = minut;
    }

    public PlimbareMasaAlarma() {
    }

    public long getOra() {
        return ora;
    }

    public void setOra(long ora) {
        this.ora = ora;
    }

    public long getMinut() {
        return minut;
    }

    public void setMinut(long minut) {
        this.minut = minut;
    }

    @Override
    public String toString() {
        return "PlimbareMasaAlarma{" +
                "idUser='" + idUser + '\'' +
                ", ora=" + ora +
                ", minut=" + minut +
                '}';
    }
}
