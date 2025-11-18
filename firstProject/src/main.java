import java.time.LocalDate;

public class main {

    
    public static class Car {
        protected String make;
        protected String model;
        protected int year;
        protected double mileage;
        protected double fuelLevel;

        public static final double MAX_FUEL = 60.0;
        public static final double DEFAULT_CONSUMPTION = 8.0;

        
        public Car() {

        }

        
        public Car(String make, String model, int year, double mileage, double fuelLevel) {
            this.make = make;
            this.model = model;

            if (year < 1886 || year > LocalDate.now().getYear()) {
                System.out.println("⚠ Invalid year, set to current year");
                this.year = LocalDate.now().getYear();
            } else {
                this.year = year;
            }

            this.mileage = Math.max(mileage, 0);
            this.fuelLevel = Math.min(Math.max(fuelLevel, 0), MAX_FUEL);
        }

        public Car(String make, String model) {
            this(make, model, LocalDate.now().getYear(), 0.0, 0.0);
        }

    
        public double drive(double km) {
            if (km <= 0) {
                System.out.println(" Invalid km");
                return 0;
            }
            double neededFuel = km * DEFAULT_CONSUMPTION / 100.0;
            if (neededFuel <= fuelLevel) {
                mileage += km;
                fuelLevel -= neededFuel;
                System.out.println(  make + " " + model + " drove " + km + " km.");
                return km;
            } else {
                System.out.println(" Not enough fuel!");
                return 0;
            }
        }

        
        public void refuel(double liters) {
            if (liters <= 0) {
                System.out.println(" Invalid liters");
            } else {
                fuelLevel = Math.min(MAX_FUEL, fuelLevel + liters);
                System.out.println(" Refueled: " + liters + "L. Current: " + fuelLevel + "L");
            }
        }

        public int getAge() {
            return LocalDate.now().getYear() - this.year;
        }

        @Override
        public String toString() {
            return """
                     Car Info:
                       • Make: %s
                       • Model: %s
                       • Year: %d (Age: %d years)
                       • Mileage: %.1f km
                       • Fuel Level: %.1f L / %.1f L
                    """.formatted(make, model, year, getAge(), mileage, fuelLevel, MAX_FUEL);
        }
    }

    
    public static class ElectricCar extends Car {
        private int batteryLevel; 

        public ElectricCar(String make, String model, int year, double mileage, int batteryLevel) {
            super(make, model, year, mileage, 0); // вызываем конструктор Car
            this.batteryLevel = Math.min(Math.max(batteryLevel, 0), 100);
        }

        public void charge() {
            batteryLevel = 100;
            System.out.println(" " + make + " " + model + " is now fully charged!");
        }

        @Override
        public double drive(double km) {
            int neededBattery = (int) (km / 2); // 1% батареи = 2 км
            if (batteryLevel >= neededBattery) {
                mileage += km;
                batteryLevel -= neededBattery;
                System.out.println("⚡ " + make + " " + model + " drove " + km + " km silently.");
                return km;
            } else {
                System.out.println(" Battery too low! Please charge " + make + " " + model);
                return 0;
            }
        }

        @Override
        public String toString() {
            return """
                    ⚡ Electric Car Info:
                       • Make: %s
                       • Model: %s
                       • Year: %d (Age: %d years)
                       • Mileage: %.1f km
                       • Battery: %d%%
                    """.formatted(make, model, year, getAge(), mileage, batteryLevel);
        }
    }


    public static void main(String[] args) {
        Car bmw = new Car("BMW", "M5", 2018, 120000, 40);
        ElectricCar tesla = new ElectricCar("Tesla", "Model S", 2023, 20000, 80);

        System.out.println(bmw);
        System.out.println(tesla);

        System.out.println("=== Driving Simulation ===");
        bmw.drive(100);
        tesla.drive(150);

        tesla.charge();
        tesla.drive(200);

        System.out.println("\n=== Final Status ===");
        System.out.println(bmw);
        System.out.println(tesla);
    }
}

