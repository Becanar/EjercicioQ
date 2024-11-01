package com.example.ejercicioq;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.util.Timer;
import java.util.TimerTask;
import java.io.IOException;

public class Temporizador extends AnchorPane {
    private BooleanProperty fin;
    private int segundos;
    private Timer timer;

    @FXML
    private Label m1;

    @FXML
    private Label m2;

    @FXML
    private Label s1;

    @FXML
    private Label s2;

   public Temporizador() {
        this.fin = new SimpleBooleanProperty(false);
        this.segundos = -1;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Temporizador.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean setSegundos(int segundos) {
        if (segundos >= 60) {
            int minutos = (int) (segundos / 60);
            if (minutos < 100) {
                this.segundos = segundos;
                return true;
            }
        } else {
            if (segundos > 0) {
                this.segundos = segundos;
                return true;
            }
        }
        return false;
    }

    public void iniciar() {
        if (this.segundos == -1) {
            System.err.println("Asigna los segundos antes de iniciar el temporizador");
        } else {
            final int[] restante = {this.segundos};
            timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    if (restante[0] < 0) {
                        timer.cancel();
                        Platform.runLater(() -> fin.set(true));
                        return;
                    }
                    int mins = restante[0] / 60;
                    int mins1 = mins / 10;
                    int mins2 = mins % 10;
                    int segs = restante[0] % 60;
                    int segs1 = segs / 10;
                    int segs2 = segs % 10;
                    // Usar Platform.runLater para actualizar los labels del temporizador
                    Platform.runLater(() -> {
                        m1.setText(mins1 + "");
                        m2.setText(mins2 + "");
                        s1.setText(segs1 + "");
                        s2.setText(segs2 + "");
                    });
                    restante[0] -= 1;
                }
            }, 0, 1000);
        }
    }

    public void parar() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
        }
    }

    public BooleanProperty fin() {
        return fin;
    }


}