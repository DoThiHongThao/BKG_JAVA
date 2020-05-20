package dtp.effect;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Animation {
    private String name;
    private boolean isRepeated;
    private int currentFrame;
    private long beginTime;
    private final boolean drawRectFrame;
    private final ArrayList<FrameImage> frameImages;
    private final ArrayList<Boolean> ignoreFrames;
    private final ArrayList<Double> delayFrames;

    public Animation() {
        delayFrames = new ArrayList<Double>();
        beginTime = 0;
        currentFrame = 0;

        ignoreFrames = new ArrayList<Boolean>();
        frameImages = new ArrayList<FrameImage>();
        drawRectFrame = false;
        isRepeated = true;
    }

    public Animation(final Animation animation) {
        beginTime = animation.beginTime;
        currentFrame = animation.currentFrame;
        drawRectFrame = animation.drawRectFrame;
        isRepeated = animation.isRepeated;

        delayFrames = new ArrayList<Double>();
        for (final Double d : animation.delayFrames) {
            delayFrames.add(d);
        }

        ignoreFrames = new ArrayList<Boolean>();
        for (final boolean b : animation.ignoreFrames) {
            ignoreFrames.add(b);
        }

        frameImages = new ArrayList<FrameImage>();
        for (final FrameImage f : animation.frameImages) {
            frameImages.add(new FrameImage(f));
        }
    }

    public void setIsRepeated(final boolean isRepeated) {
        this.isRepeated = isRepeated;
    }

    public boolean isIgnoreFrame(final int id) {
        return ignoreFrames.get(id);
    }

    public void setIgnoreFrame(final int id) {
        if (id >= 0 && id < ignoreFrames.size())
            ignoreFrames.set(id, true);
    }

    public void unIgnoreFrame(final int id) {
        if (id >= 0 && id < ignoreFrames.size())
            ignoreFrames.set(id, false);
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setCurrentFrame(final int currentFrame) {
        if (currentFrame >= 0 && currentFrame < frameImages.size())
            this.currentFrame = currentFrame;
        else this.currentFrame = 0;
    }

    public int getCurrentFrame() {
        return this.currentFrame;
    }

    public void reset() {
        currentFrame = 0;
        beginTime = 0;
    }

    public void add(final FrameImage frameImage, final double timeToNextFrame) {
        ignoreFrames.add(false);
        frameImages.add(frameImage);
        delayFrames.add(new Double(timeToNextFrame));
    }

    public BufferedImage getCurrentImage() {
        return frameImages.get(currentFrame).getImage();
    }

    public void Update(final long deltaTime) {
        if (beginTime == 0) beginTime = deltaTime;
        else {
            if (deltaTime - beginTime > delayFrames.get(currentFrame)) {
                nextFrame();
                beginTime = deltaTime;
            }
        }
    }

    public boolean isLastFrame() {
        if (currentFrame == frameImages.size() - 1)
            return true;
        else return false;
    }

    private void nextFrame() {
        if (currentFrame >= frameImages.size() - 1) {
            if (isRepeated) currentFrame = 0;
        } else currentFrame++;
        if (ignoreFrames.get(currentFrame)) nextFrame();
    }

    public void flipAllImage() {
        for (int i = 0; i < frameImages.size(); i++) {
            BufferedImage image = frameImages.get(i).getImage();

            final AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
            tx.translate(-image.getWidth(), 0);

            final AffineTransformOp op = new AffineTransformOp(tx,
                    AffineTransformOp.TYPE_BILINEAR);
            image = op.filter(image, null);

            frameImages.get(i).setImage(image);
        }
    }

    public void draw(final int x, final int y, final Graphics2D g2) {
        final BufferedImage image = getCurrentImage();
        g2.drawImage(image, x - image.getWidth() / 2, y - image.getHeight() / 2, null);
        if (drawRectFrame) {
            g2.drawRect(x - image.getWidth() / 2, x - image.getWidth() / 2, image.getWidth(), image.getHeight());
        }
    }
}

