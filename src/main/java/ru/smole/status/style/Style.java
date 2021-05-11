package ru.smole.status.style;

import ru.smole.render.RColor;

public interface Style {

    Style setBackground(RColor color);

    Style setColor(RColor color);

    RColor getBackground();

    RColor getColor();

    class Default implements Style {

        private RColor background;
        private RColor color;

        Default(RColor color) {
            this(color, RColor.WHITE);
        }

        Default(RColor color, RColor background) {
            this.color = color;
            this.background = background;
        }

        public Style setBackground(RColor color) {
            background = color;
            return this;
        }

        public Style setColor(RColor color) {
            this.color = color;
            return this;
        }

        public RColor getBackground() {
            return background;
        }

        public RColor getColor() {
            return color;
        }
    }

    static Style of(RColor color) {
        return new Default(color);
    }

    static Style of(RColor color, RColor background) {
        return new Default(color, background);
    }

}
