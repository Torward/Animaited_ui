package sample;

import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;

public class MilkGlassPane extends Pane {
    private final Pane container;
    private final BoxBlur blur;
    private final double initBlur = 15;
    private WritableImage image;

    public MilkGlassPane(Pane container) {
        //эффект размытия
        blur = new BoxBlur(initBlur, initBlur, 3);
        setEffect(blur);
        blur.setInput(new ColorAdjust(0, 0, 0.4, 0));

        this.container = container;

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long time) {
                updateBackground();
            }
        };
        timer.start();
        
    }

    private void updateBackground() {
        if (getWidth() <= 0 || getHeight() <= 0 || this.getOpacity() == 0) {
            return;
        }
        if (image == null
                || getWidth() != image.getWidth()
                || getHeight() != image.getHeight()) {
            image = new WritableImage((int) (this.getWidth()), (int) (this.getHeight()));
            BackgroundImage backgroundImage = new BackgroundImage(
                    image,
                    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
            setBackground(new Background(backgroundImage));
        }
        //location this Pane
        double x = getLayoutX();
        double y = getLayoutY();

        SnapshotParameters sp =new SnapshotParameters();
        Rectangle2D rect = new Rectangle2D(x,y,getWidth(),getHeight());
        sp.setViewport(rect);
        container.snapshot(sp,image);

    }

//    public void setMaxSize(int i, int i1) {
//    }


}
