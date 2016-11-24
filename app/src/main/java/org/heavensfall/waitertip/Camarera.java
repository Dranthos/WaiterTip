package org.heavensfall.waitertip;

public class Camarera {
    private String _nombre = "test", _horas, _resta;
    public boolean activa = true;
    public boolean error = false;
    public String error_S;

    public String GetNombre() {
        return _nombre;
    }

    public void SetNombre(String nombre){
        if (isEmptyString(nombre)) error = true;
        else _nombre = nombre;
    }

    public int GetHoras() {
        return Integer.parseInt(_horas);
    }

    public void SetHoras(String horas) {
        if (isEmptyString(horas)) {
            error = true;
            error_S = "Las horas introducidas para " + _nombre + " son invalidas";
        }
        else {
            error = false;
            _horas = horas;
        }

    }

    public int GetResta() {
        if(isEmptyString(_resta)) _resta = "0";

        return Integer.parseInt(_resta);
    }

    public void SetResta(String resta) {
        if (isEmptyString(resta)) {
            _resta = "0";
        }
        else _resta = resta;
    }

    private static boolean isEmptyString(String text) {
        return (text == null || text.trim().equals("null") || text.trim()
                .length() <= 0);
    }
}
