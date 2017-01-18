package br.unifor.pin.saa.entity;

/**
 * Classe responsavel pelos itens e icones do menu NavigationDrawer
 * @author Victor Sales
 * Created by Victor Sales on 03/01/2017.
 */
public class MenuItem {

    private int icon;
    private String nome;
    private boolean selected;

    public MenuItem(int icon, String nome) {
        this.icon = icon;
        this.nome = nome;
        this.selected = false;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
