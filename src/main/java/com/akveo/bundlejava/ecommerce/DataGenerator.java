package com.akveo.bundlejava.ecommerce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class DataGenerator {
    private Random rnd;
    private StringBuilder sqlStatement;
    private Map<String, String> countries;
    private static final String SCHEMA = "demo_core";
    private static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";
    private static final int CREATED_BY_USER_ID = 1;
    private static final int UPDATED_BY_USER_ID = 1;

    private String getCurrentLocalDateTimeStamp() {
        return LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern(DATE_PATTERN));
    }

    public DataGenerator() {
        rnd = new Random(System.currentTimeMillis());
        sqlStatement = new StringBuilder();
        countries = getCountriesMap();
    }

    private String generateRandomDate(LocalDateTime startDate, LocalDateTime endDate) {

        if (startDate == null) {
            startDate = LocalDateTime.of(2009, Month.JANUARY, 1, 1, 1);
        }
        if (endDate == null) {
            endDate = LocalDateTime.now();
        }
        int deltaYears = endDate.getYear() - startDate.getYear();
        int range = endDate.getDayOfYear() - startDate.getDayOfYear() + deltaYears * 365;

        startDate = startDate.plusDays(generateInt(1, range));


        return startDate.format(DateTimeFormatter.ofPattern(DATE_PATTERN));
    }

    private int generateInt(int minFacet, int rightFacet) {
        return minFacet + rnd.nextInt(rightFacet - minFacet + 1);
    }

    private int generateRandomStatus() {
        return generateInt(1, 3);
    }

    private int generateRandomType() {
        return generateInt(1, 5);
    }

    private BigDecimal generateRandomValue() {
        BigDecimal value = new BigDecimal(rnd.nextDouble() * 1000);
        return value.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    //    string RandomValue()
//    {
//
//        return value.ToString(System.Globalization.CultureInfo.InvariantCulture);
//    }

    private void appendCountries(Map<String, String> countries) {
        sqlStatement.append("INSERT INTO country (code, name) VALUES");
        String countriesStatement = countries.entrySet().stream()
                .map(entry -> "('" + entry.getKey() + "', '" + entry.getValue() + "')")
                .collect(Collectors.joining(","));
        sqlStatement.append(countriesStatement);
        sqlStatement.append(";");
    }

    private void appendOrders() {
        LocalDateTime startDate = LocalDateTime.of(2013, Month.JANUARY, 1, 1, 1);
        sqlStatement.append("INSERT INTO orders (name, created_date, updated_date, date, value, currency," +
                " type, status, country_id, created_by_user_id, updated_by_user_id) VALUES ");

        String orderStr = Stream.generate(() -> 0)
                .limit(2000)
                .map(entry -> "('Order " + generateInt(1, 20000) + "', '" +
                        getCurrentLocalDateTimeStamp() + "', '" + getCurrentLocalDateTimeStamp() + "', '" +
                        generateRandomDate(startDate, null) + "', " +  generateRandomValue() + ", 'USD', " + generateRandomType()+ ", " +
                        generateRandomStatus() + ", " + generateInt(1, countries.size()) + ", " +
                        CREATED_BY_USER_ID + ", " + UPDATED_BY_USER_ID + ")")
                .collect(Collectors.joining(","));
        sqlStatement.append(orderStr);
        sqlStatement.append(";");
    }

    private void appendUserActivities() {
        sqlStatement.append("INSERT INTO user_activity (user_id, date, url) values ");
        String userActivitiesStr = Stream.generate(() -> 1)
                .limit(1000)
                .map(entry -> "(" + generateInt(1, 4) + ", '" + generateRandomDate(null, null) + "', 'url " +
                        generateInt(1, 50) + "')")
                .collect(Collectors.joining(","));
        sqlStatement.append(userActivitiesStr);
        sqlStatement.append(";");
    }

    private void appendTraffic() {
        sqlStatement.append("INSERT INTO traffic (date, value) values ");
        String trafficStr = Stream.generate(() -> 0)
                .limit(1000)
                .map(entry -> "('" + generateRandomDate(null, null) + "', " + generateInt(1, 100) + ")")
                .collect(Collectors.joining(","));
        sqlStatement.append(trafficStr);
        sqlStatement.append(";");
    }

    @Autowired
    @EventListener(ContextStartedEvent.class)
    public void generateData(EntityManagerFactory emf) {
        appendCountries(getCountriesMap());
        appendUserActivities();
        appendTraffic();
        appendOrders();
        doSqlQuery(emf.createEntityManager());
    }

    public void doSqlQuery(EntityManager entityManager) {
        entityManager.getTransaction().begin();
        entityManager.createNativeQuery(sqlStatement.toString()).executeUpdate();

        entityManager.getTransaction().commit();
    }

    private Map<String, String> getCountriesMap() {
        return new HashMap<String, String>() {{
            put("AFG", "Afghanistan");
            put("AGO", "Angola");
            put("ALB", "Albania");
            put("ARE", "United Arab Emirates");
            put("ARG", "Argentina");
            put("ARM", "Armenia");
            put("ATA", "Antarctica");
            put("ATF", "French Southern and Antarctic Lands");
            put("AUS", "Australia");
            put("AUT", "Austria");
            put("AZE", "Azerbaijan");
            put("BDI", "Burundi");
            put("BEL", "Belgium");
            put("BEN", "Benin");
            put("BFA", "Burkina Faso");
            put("BGD", "Bangladesh");
            put("BGR", "Bulgaria");
            put("BHS", "The Bahamas");
            put("BIH", "Bosnia and Herzegovina");
            put("BLR", "Belarus");
            put("BLZ", "Belize");
            put("BMU", "Bermuda");
            put("BOL", "Bolivia");
            put("BRA", "Brazil");
            put("BRN", "Brunei");
            put("BTN", "Bhutan");
            put("BWA", "Botswana");
            put("CAF", "Central African Republic");
            put("CAN", "Canada");
            put("CHE", "Switzerland");
            put("CHL", "Chile");
            put("CHN", "China");
            put("CIV", "Ivory Coast");
            put("CMR", "Cameroon");
            put("COD", "Democratic Republic of the Congo");
            put("COG", "Republic of the Congo");
            put("COL", "Colombia");
            put("CRI", "Costa Rica");
            put("CUB", "Cuba");
            put("CYP", "Cyprus");
            put("CZE", "Czech Republic");
            put("DEU", "Germany");
            put("DJI", "Djibouti");
            put("DNK", "Denmark");
            put("DOM", "Dominican Republic");
            put("DZA", "Algeria");
            put("ECU", "Ecuador");
            put("EGY", "Egypt");
            put("ERI", "Eritrea");
            put("ESP", "Spain");
            put("EST", "Estonia");
            put("ETH", "Ethiopia");
            put("FIN", "Finland");
            put("FJI", "Fiji");
            put("FLK", "Falkland Islands");
            put("FRA", "France");
            put("GAB", "Gabon");
            put("GBR", "United Kingdom");
            put("GEO", "Georgia");
            put("GHA", "Ghana");
            put("GIN", "Guinea");
            put("GMB", "Gambia");
            put("GNB", "Guinea Bissau");
            put("GNQ", "Equatorial Guinea");
            put("GRC", "Greece");
            put("GRL", "Greenland");
            put("GTM", "Guatemala");
            put("GUF", "French Guiana");
            put("GUY", "Guyana");
            put("HND", "Honduras");
            put("HRV", "Croatia");
            put("HTI", "Haiti");
            put("HUN", "Hungary");
            put("IDN", "Indonesia");
            put("IND", "India");
            put("IRL", "Ireland");
            put("IRN", "Iran");
            put("IRQ", "Iraq");
            put("ISL", "Iceland");
            put("ISR", "Israel");
            put("ITA", "Italy");
            put("JAM", "Jamaica");
            put("JOR", "Jordan");
            put("JPN", "Japan");
            put("KAZ", "Kazakhstan");
            put("KEN", "Kenya");
            put("KGZ", "Kyrgyzstan");
            put("KHM", "Cambodia");
            put("KOR", "South Korea");
            put("KWT", "Kuwait");
            put("LAO", "Laos");
            put("LBN", "Lebanon");
            put("LBR", "Liberia");
            put("LBY", "Libya");
            put("LKA", "Sri Lanka");
            put("LSO", "Lesotho");
            put("LTU", "Lithuania");
            put("LUX", "Luxembourg");
            put("LVA", "Latvia");
            put("MAR", "Morocco");
            put("MDA", "Moldova");
            put("MDG", "Madagascar");
            put("MEX", "Mexico");
            put("MKD", "Macedonia");
            put("MLI", "Mali");
            put("MLT", "Malta");
            put("MMR", "Myanmar");
            put("MNE", "Montenegro");
            put("MNG", "Mongolia");
            put("MOZ", "Mozambique");
            put("MRT", "Mauritania");
            put("MWI", "Malawi");
            put("MYS", "Malaysia");
            put("NAM", "Namibia");
            put("NCL", "New Caledonia");
            put("NER", "Niger");
            put("NGA", "Nigeria");
            put("NIC", "Nicaragua");
            put("NLD", "Netherlands");
            put("NOR", "Norway");
            put("NPL", "Nepal");
            put("NZL", "New Zealand");
            put("OMN", "Oman");
            put("PAK", "Pakistan");
            put("PAN", "Panama");
            put("PER", "Peru");
            put("PHL", "Philippines");
            put("PNG", "Papua New Guinea");
            put("POL", "Poland");
            put("PRI", "Puerto Rico");
            put("PRK", "North Korea");
            put("PRT", "Portugal");
            put("PRY", "Paraguay");
            put("QAT", "Qatar");
            put("ROU", "Romania");
            put("RUS", "Russia");
            put("RWA", "Rwanda");
            put("ESH", "Western Sahara");
            put("SAU", "Saudi Arabia");
            put("SDN", "Sudan");
            put("SSD", "South Sudan");
            put("SEN", "Senegal");
            put("SLB", "Solomon Islands");
            put("SLE", "Sierra Leone");
            put("SLV", "El Salvador");
            put("SOM", "Somalia");
            put("SRB", "Republic of Serbia");
            put("SUR", "Suriname");
            put("SVK", "Slovakia");
            put("SVN", "Slovenia");
            put("SWE", "Sweden");
            put("SWZ", "Swaziland");
            put("SYR", "Syria");
            put("TCD", "Chad");
            put("TGO", "Togo");
            put("THA", "Thailand");
            put("TJK", "Tajikistan");
            put("TKM", "Turkmenistan");
            put("TLS", "East Timor");
            put("TTO", "Trinidad and Tobago");
            put("TUN", "Tunisia");
            put("TUR", "Turkey");
            put("TWN", "Taiwan");
            put("TZA", "United Republic of Tanzania");
            put("UGA", "Uganda");
            put("UKR", "Ukraine");
            put("URY", "Uruguay");
            put("USA", "United States of America");
            put("UZB", "Uzbekistan");
            put("VEN", "Venezuela");
            put("VNM", "Vietnam");
            put("VUT", "Vanuatu");
            put("PSE", "West Bank");
            put("YEM", "Yemen");
            put("ZAF", "South Africa");
            put("ZMB", "Zambia");
            put("ZWE", "Zimbabwe");
        }};
    }

//    private static List<String> getNamesList(int count) {
//        return Stream.of("Rostand Simon", "Petulia Samuel", "Bacon Mathghamhain", "Adlei Michael", "Damian IvorJohn", "Fredenburg Neil", "Strader O''Neal", "Meill Donnell", "Crowell O''Donnell",
//                "Lenssen Rory", "Jac Names", "Budge Mahoney", "Bondy Simon", "Bilow Ahearn", "Weintrob Farrell", "Tristan Cathasach", "Baxy Bradach", "Utta Damhan", "Brag Treasach",
//                "Vallie Kelly", "Trutko Aodha", "Mellins Cennetig", "Zurek Casey", "Star O''Neal", "Hoffer Names", "Sturges Macshuibhne", "Lifton Sioda", "Rochell Ahearn", "Lynsey Mcmahon",
//                "Delp Seaghdha", "Sproul O''Brien", "Omar Ahearn", "Keriann Bhrighde", "Killoran Sullivan", "Olette Riagain", "Kohn Gorman", "Shimberg Maurice", "Nalda Aodh",
//                "Livvie Casey", "Zoie Treasach", "Kletter Daly", "Sandy Mckay", "Ern O''Neal", "Loats Maciomhair", "Marlena Mulryan", "Hoshi Naoimhin", "Schmitt Whalen",
//                "Patterson Raghailligh", "Ardeen Kelly", "Rasla Simon", "Douty Cennetig", "Giguere Names", "Dorina Clark", "Rothmuller Simon", "Shabbir Delaney", "Placidia Bradach",
//                "Savior Keefe", "Concettina Maguire", "Malynda Muirchertach", "Vanzant Fearghal", "Schroder Ruaidh", "Ainslie Ciardha", "Richter Colman", "Gianni Macghabhann",
//                "Norvan O''Boyle", "Polak Mcneil", "Bridges Macghabhann", "Eisenberg Samuel", "Thenna Daly", "Moina Mcmahon", "Gasper Whelan", "Hutt O''Keefe", "Quintin Names",
//                "Towny Reynold", "Viviane Ceallachan", "Girovard Power", "Fanchon Flann", "Nickolai Meadhra", "Polash Login", "Cacilia Macghabhann", "Chaffee Rory", "Baseler Conchobhar",
//                "Amathiste Cuidightheach", "Ishii Riagain", "Marieann Damhan", "Rangel Dubhain", "Alarick Fionn", "Humfrey Rory", "Mable O''Mooney", "Jemie Macdermott", "Hogen Rhys",
//                "Cazzie Mohan", "Airlie Reynold", "Safire O''Hannigain", "Strephonn Nuallan", "Brion Eoghan", "Banquer Seaghdha", "Sedgewinn Mcguire", "Alma Macghabhann", "Durward Mcnab").collect(Collectors.toList());
//    }
}
