package com.dani.universidades;

public class Universidad {

    private String country;
    private String webPage;
    private String name;
    private String codPais;
    private String msg;

    public Universidad(String country, String webPage, String name, String codPais) {
        this.country = country;
        this.webPage = webPage;
        this.name = name;
        this.codPais = codPais;
    }

    public Universidad() {

    }
    public Universidad(String msg) {
      this.name=msg;
    }
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getWebPage() {
        return webPage;
    }

    public void setWebPage(String webPage) {
        this.webPage = webPage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCodPais() {
        return codPais;
    }

    public void setCodPais(String codPais) {
        this.codPais = codPais;
    }
}
