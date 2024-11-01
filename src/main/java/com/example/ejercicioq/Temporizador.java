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

/**
 * Clase Temporizador que extiende AnchorPane y representa un temporizador de cuenta regresiva.
 * Esta clase permite configurar un tiempo en segundos, iniciar y detener el temporizador,
 * y actualizar visualmente los minutos y segundos restantes en la interfaz de usuario.
 */
public class Temporizador extends AnchorPane {
    /**
     * Propiedad boolean que indica si el temporizador ha finalizado.
     */
    private BooleanProperty fin;

    /**
     * Tiempo inicial en segundos para el temporizador.
     */
    private int segundos;

    /**
     * Objeto Timer utilizado para manejar el conteo regresivo en intervalos de tiempo.
     */
    private Timer timer;

    @FXML
    private Label m1; // Dígito de las decenas de minutos
    @FXML
    private Label m2; // Dígito de las unidades de minutos
    @FXML
    private Label s1; // Dígito de las decenas de segundos
    @FXML
    private Label s2; // Dígito de las unidades de segundos

    /**
     * Constructor de la clase Temporizador.
     * Inicializa la propiedad {@code fin} en falso, asigna el tiempo inicial como no configurado (-1)
     * y carga la interfaz desde el archivo FXML.
     */
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

    /**
     * Configura el tiempo inicial del temporizador en segundos.
     * Si el valor es mayor o igual a 60, se permite configurar hasta un máximo de 99 minutos.
     *
     * @param segundos El tiempo en segundos que se desea configurar para el temporizador.
     * @return {@code true} si los segundos son válidos y se configuraron correctamente;
     *         {@code false} si los segundos están fuera de los límites permitidos.
     */
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

    /**
     * Inicia el temporizador en cuenta regresiva a partir del tiempo configurado.
     * Si el tiempo no ha sido configurado (es igual a -1), muestra un mensaje de error.
     * Al finalizar la cuenta regresiva, la propiedad {@code fin} se establece en verdadero.
     */
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

    /**
     * Detiene el temporizador y libera los recursos asociados.
     * Este método puede ser llamado en cualquier momento para detener la cuenta regresiva.
     */
    public void parar() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
        }
    }

    /**
     * Obtiene la propiedad {@code fin} que indica si el temporizador ha finalizado.
     *
     * @return La propiedad {@code BooleanProperty} {@code fin}.
     */
    public BooleanProperty fin() {
        return fin;
    }
}
