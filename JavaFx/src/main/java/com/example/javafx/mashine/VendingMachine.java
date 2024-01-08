package com.example.javafx.mashine;

import lombok.Getter;

public class VendingMachine {
    @Getter
    public static class Ingredients {
        public enum IngredientName {
            MILK(80, 0, 0), COFFEE(20, 0, 1), TEA(50, 0, 2),
            ISE_CREAM(40, 0, 3), CREAM(30, 0, 4), SURGE(70, 0, 5),
            WATER(300, 0, 6), END(0, 0, 7);
            private final int max;
            private final int min;
            @Getter
            private final int index;

            IngredientName(int max, int min, int index) {
                this.max = max;
                this.min = min;
                this.index = index;
            }

            public float progress(int count) {
                return (float) (count) / max;
            }

            public static IngredientName byIndex(int index) {
                assert index < IngredientName.values().length && index >= 0;
                return IngredientName.values()[index];
            }
        }

        public static final int COUNT_OF_INGREDIENTS = 7;
        int[] ingredients = new int[COUNT_OF_INGREDIENTS];

        Ingredients() {
            for (int ingredientIndex = 0; ingredientIndex < ingredients.length; ingredientIndex++) {
                ingredients[ingredientIndex] = IngredientName.byIndex(ingredientIndex).max / 2;
            }
        }

        boolean isIngredientEnough(int count, IngredientName name) {
            return ((ingredients[name.index] - count) >= name.min);
        }

        boolean isIngredientFull(int count, IngredientName name) {
            return ingredients[name.index] + count > name.max;
        }

        void raiseIngredient(int count, IngredientName name) {
            if (!isIngredientEnough(count, name)) {
                return;
            }
            ingredients[name.index] -= count;
        }

        void addIngredient(int count, IngredientName name) {
            if (isIngredientFull(count, name)) {
                return;
            }
            ingredients[name.index] += count;
        }

        static boolean isEndIndex(int count) {
            return IngredientName.END.index == count;
        }

        static final int RAISE_VALUE = 1;
        static final int ADD_VALUE = 1;

        void changeIngredientValue(int index, boolean isServiceState) {
            if (!Ingredients.isEndIndex(index)) {
                if (isServiceState) {
                    addIngredient(ADD_VALUE, IngredientName.byIndex(index));
                    ingredients[index] += ADD_VALUE;
                    return;
                }
                raiseIngredient(RAISE_VALUE, IngredientName.byIndex(index));
            }
        }

        boolean isUseIngredient(IngredientName name, State state) {
            switch (state) {
                case SERVICE -> {
                    return !isIngredientFull(ADD_VALUE, name);
                }
                case NO -> {
                    return false;
                }
                default -> {
                    return isIngredientEnough(RAISE_VALUE, name);
                }
            }
        }
    }

    @Getter
    Ingredients ingredients = new Ingredients();

    @Getter
    State[][] states={
            {State.ADDS_MILK,  State.NO,         State.NO,      State.NO,            State.NO,        State.NO,        State.ADDS_WATER,State.SERVICE},
            {State.NO,         State.ADDS_COFFEE,State.ADDS_TEA,State.NO,            State.NO,        State.NO,        State.ADDS_WATER,State.COUNT  },
            {State.NO,         State.NO,         State.NO,      State.NO,            State.NO,        State.ADDS_SURGE,State.NO,        State.COUNT  },
            {State.ADDS_MILK,  State.NO,         State.NO,      State.NO,            State.NO,        State.ADDS_SURGE,State.NO,        State.COUNT  },
            {State.NO,         State.NO,         State.NO,      State.ADDS_ISE_CREAM,State.NO,        State.ADDS_SURGE,State.NO,        State.COUNT  },
            {State.NO,         State.NO,         State.NO,      State.NO,            State.ADDS_CREAM,State.ADDS_SURGE,State.NO,        State.COUNT  },
            {State.ADDS_MILK,  State.NO,         State.NO,      State.NO,            State.NO,        State.ADDS_SURGE,State.NO,        State.COUNT  },
            {State.ADDS_MILK,  State.NO,         State.NO,      State.ADDS_ISE_CREAM,State.ADDS_CREAM,State.ADDS_SURGE,State.NO,        State.COUNT  },
            {State.NO,         State.NO,         State.NO,      State.NO,            State.NO,        State.NO,        State.NO,        State.WAITING},
            {State.SERVICE,    State.SERVICE,    State.SERVICE, State.SERVICE,       State.SERVICE,   State.SERVICE,   State.SERVICE,   State.WAITING}
    };

    public enum State {
        WAITING(0),
        ADDS_WATER(1),
        ADDS_SURGE(2),
        ADDS_MILK(3),
        ADDS_ISE_CREAM(4),
        ADDS_CREAM(5),
        ADDS_TEA(6),
        ADDS_COFFEE(7),
        COUNT(8),
        SERVICE(9),
        NO(10);
        @Getter
        final int index;

        State(int index) {
            this.index = index;
        }

        static boolean isService(State state) {
            return state == SERVICE;
        }

        static State byIndex(int index) {
            assert index < State.values().length;
            assert index > -2;
            if (index == -1) {
                return State.values()[State.values().length - 1];
            }
            return State.values()[index];
        }
    }

    @Getter
    State state = State.WAITING;

    public void changeState(int index) {
        ingredients.changeIngredientValue(index, State.isService(state));
        state = states[state.index][index];
    }

    State[] serviceState = {State.ADDS_MILK, State.ADDS_COFFEE, State.ADDS_TEA, State.ADDS_ISE_CREAM, State.ADDS_CREAM, State.ADDS_SURGE, State.ADDS_WATER, State.WAITING};

    public State[] getCurrentState() {
        if (State.SERVICE == state) {
            return serviceState;
        }
        return states[state.index];
    }

    public boolean isIngredientEnough(int index) {

        if (Ingredients.IngredientName.byIndex(index) == Ingredients.IngredientName.END) {
            return true;
        }
        if (state != State.SERVICE) {
            return ingredients.isIngredientEnough(Ingredients.RAISE_VALUE, Ingredients.IngredientName.byIndex(index));
        }
        return !ingredients.isIngredientFull(Ingredients.ADD_VALUE, Ingredients.IngredientName.byIndex(index));
    }
}
