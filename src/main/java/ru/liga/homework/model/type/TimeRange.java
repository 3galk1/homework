package ru.liga.homework.model.type;


import lombok.Getter;

@Getter
public enum TimeRange {
        TOMORROW(1),
        WEEK(7);

        private int value;

        TimeRange(int value) {
                this.value = value;
        }
}
