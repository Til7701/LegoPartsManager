package de.holube.legopartsmanager.lego;

public class LegoDatabase {

    private LegoDatabase() {

    }

    private static LegoColorManager legoColorManager = new LegoColorManager("colors.csv");
    private static LegoElementManager legoElementManager = new LegoElementManager("elements.csv");
    private static LegoDesignManager legoDesignManager = new LegoDesignManager("parts.csv");

    public static LegoColorManager getLegoColorManager() {
        return legoColorManager;
    }

    public static void setLegoColorManager(LegoColorManager legoColorManager) {
        LegoDatabase.legoColorManager = legoColorManager;
    }

    public static LegoElementManager getLegoElementManager() {
        return legoElementManager;
    }

    public static void setLegoElementManager(LegoElementManager legoElementManager) {
        LegoDatabase.legoElementManager = legoElementManager;
    }

    public static LegoDesignManager getLegoDesignManager() {
        return legoDesignManager;
    }

    public static void setLegoDesignManager(LegoDesignManager legoDesignManager) {
        LegoDatabase.legoDesignManager = legoDesignManager;
    }
}
