package gui;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import businessLogic.BLFacade;
import domain.Driver;
import domain.Reserva;

public class ReservasPendientesGUI extends JFrame {
    // Campos para la imagen de confirmación
    private final ImageIcon confirmacion;
    private final JLabel fotoLabel;
    
    // panel de reservas
    private final JPanel listPanel;

    public ReservasPendientesGUI(Driver driver) {
        super("Reservas Pendientes");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // 1) Cargo y escalo la imagen una sola vez
        confirmacion = loadScaledIcon("/imagen/tick.png", 100, 75);

        // 2) Preparo el JLabel que recibirá el icono al aceptar
        fotoLabel = new JLabel();
        fotoLabel.setHorizontalAlignment(JLabel.CENTER);
        fotoLabel.setPreferredSize(new Dimension(200, 150));

        // 3) Creo el panel de lista y lo pueblо
        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        rellenarLista(driver);

        // 4) Layout de la ventana
        setLayout(new BorderLayout(10, 10));
        add(new JScrollPane(listPanel), BorderLayout.CENTER);
        add(fotoLabel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

    private void rellenarLista(Driver driver) {
        listPanel.removeAll();
        BLFacade facade = MainGUI.getBusinessLogic();
        List<Reserva> reservasPendientes = facade.getReservasPendientes(driver);

        for (Reserva r : reservasPendientes) {
            JPanel fila = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel info = new JLabel(
                "Pasajero: " + r.getTraveler().getName() +
                " | Viaje: " + r.getRide().getFrom() +
                " → " + r.getRide().getTo()
            );
            JButton btnAceptar = new JButton("Aceptar");
            JButton btnRechazar = new JButton("Rechazar");

            // Al aceptar, marcamos en BD, quitamos la fila y mostramos la imagen
            btnAceptar.addActionListener(e -> {
                r.setAceptada(true);
                facade.actualizarReserva(r);

                listPanel.remove(fila);
                listPanel.revalidate();
                listPanel.repaint();

                fotoLabel.setIcon(confirmacion);
                fotoLabel.revalidate();
                fotoLabel.repaint();
            });

            // Al rechazar, eliminamos en BD y quitamos la fila (y opcionalmente mostramos un mensaje)
            btnRechazar.addActionListener(e -> {
                facade.eliminarReserva(r);

                listPanel.remove(fila);
                listPanel.revalidate();
                listPanel.repaint();

                JOptionPane.showMessageDialog(this, "Reserva rechazada.");
            });

            fila.add(info);
            fila.add(btnAceptar);
            fila.add(btnRechazar);
            listPanel.add(fila);
        }
    }

    /** Carga una imagen del classpath y la escala con calidad. */
    private ImageIcon loadScaledIcon(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(getClass().getResource(path));
        Image img = icon.getImage()
                        .getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }
}
