package sample;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {
    //Цвета кругов
    Color[] colors = {
            new Color(0.2, 0.1, 0.7, 1.0).saturate().brighter().brighter(),
            new Color(0.3, 0.4, 0.7, 1.0).saturate().brighter().brighter(),
            new Color(0.4, 0.3, 0.7, 1.0).saturate().brighter().brighter(),
            new Color(0.9, 0.7, 0.6, 0.8).saturate().brighter().brighter(),
            new Color(0.4, 0.2, 0.7, 1.0).saturate().brighter().brighter(),
    };

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Плоскость верхнего уровня
        StackPane rootPane = new StackPane();

        //Контейнер бликов
        Pane container = new Pane();

        //Контейнер бликов потомок rootPane
        rootPane.getChildren().add(container);

        //Стиль фона для контейнера бликов
        container.setStyle("-fx-background-color: rgb(25,40,80)");

        //Размеры сцены
        Scene scene = new Scene(rootPane, 1280, 800);

        //количество бликов
        int spawnNodes = 800;
        for (int i = 0; i < spawnNodes; i++) {
            spawnNode(scene, container);
        }

        //полупрозрачная панель
        MilkGlassPane milkGlassPane = new MilkGlassPane(container);
        milkGlassPane.setMaxSize(600, 400);

        //размещение полупрозрачной панели поверх rootPane
        rootPane.getChildren().add(milkGlassPane);

        primaryStage.setTitle("Milk Glass");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Spawns a node(circle)
     *
     * @param scene     scene
     * @param container circle container
     */
    private void spawnNode(Scene scene, Pane container) {
        //Ячейка с кругами
        Circle node = new Circle(0);
        node.setManaged(false);

        //Случайно выбирает цвет
        node.setFill(colors[(int) (Math.random() * colors.length)]);

        //выбор случайнойпозиции
        node.setCenterX(Math.random() * scene.getWidth());
        node.setCenterY(Math.random() * scene.getHeight());
        container.getChildren().add(node);
        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.ZERO,
                        new KeyValue(node.radiusProperty(), 0),
                        new KeyValue(node.centerXProperty(), node.getCenterX()),
                        new KeyValue(node.centerYProperty(), node.getCenterY()),
                        new KeyValue(node.opacityProperty(),  0)),
                new KeyFrame(
                        Duration.seconds(5 + Math.random() * 5),
                        new KeyValue(node.opacityProperty(), Math.random()),
                        new KeyValue(node.radiusProperty(), Math.random() *5)),
                new KeyFrame(
                        Duration.seconds(10 + Math.random() * 20),
                        new KeyValue(node.radiusProperty(), 0),
                        new KeyValue(node.centerXProperty(), Math.random() * scene.getWidth()),
                        new KeyValue(node.centerYProperty(), Math.random() * scene.getHeight()),
                        new KeyValue(node.opacityProperty(), 0))
        );
        timeline.setCycleCount(1);
        timeline.setOnFinished(
                evt->{
                    container.getChildren().remove(node);
                    spawnNode(scene, container);
                });
        timeline.play();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
