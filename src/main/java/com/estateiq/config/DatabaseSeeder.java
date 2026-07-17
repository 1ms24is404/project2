package com.estateiq.config;

import com.estateiq.entity.Property;
import com.estateiq.entity.PropertyType;
import com.estateiq.repository.PropertyRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DatabaseSeeder.class);
    private final PropertyRepository propertyRepository;

    public DatabaseSeeder(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (propertyRepository.count() == 0) {
            log.info("Database is empty. Seeding 120 properties (4 per sublocation)...");
            List<Property> properties = generateProperties();
            propertyRepository.saveAll(properties);
            log.info("Successfully seeded {} properties!", properties.size());
        } else {
            log.info("Properties already exist in database (count={}). Skipping seeding.", propertyRepository.count());
        }
    }

    private List<Property> generateProperties() {
        List<Property> list = new ArrayList<>();

        // Structured cities and sublocations mapping matching prompt
        Map<String, List<String>> cityMap = Map.of(
            "Bangalore", List.of("Whitefield", "Electronic City", "Koramangala", "Indiranagar", "HSR Layout"),
            "Mumbai", List.of("Bandra", "Andheri", "Powai", "Navi Mumbai", "Thane"),
            "Hyderabad", List.of("HITEC City", "Gachibowli", "Kondapur", "Jubilee Hills", "Madhapur"),
            "Pune", List.of("Hinjawadi", "Baner", "Wakad", "Kharadi", "Viman Nagar"),
            "Chennai", List.of("OMR", "Velachery", "Anna Nagar", "Tambaram", "Sholinganallur"),
            "Delhi NCR", List.of("Gurgaon", "Noida", "Dwarka", "Greater Noida", "Rohini")
        );

        // Predefined royalty-free image arrays to pick from
        String[] flatImages = {
            "https://images.unsplash.com/photo-1545324418-cc1a3fa10c00?w=600&auto=format&fit=crop&q=80",
            "https://images.unsplash.com/photo-1502672260266-1c1ef2d93688?w=600&auto=format&fit=crop&q=80",
            "https://images.unsplash.com/photo-1560448204-e02f11c3d0e2?w=600&auto=format&fit=crop&q=80",
            "https://images.unsplash.com/photo-1522708323590-d24dbb6b0267?w=600&auto=format&fit=crop&q=80"
        };
        String[] villaImages = {
            "https://images.unsplash.com/photo-1600585154340-be6161a56a0c?w=600&auto=format&fit=crop&q=80",
            "https://images.unsplash.com/photo-1600596542815-ffad4c1539a9?w=600&auto=format&fit=crop&q=80",
            "https://images.unsplash.com/photo-1600585154526-990dced4db0d?w=600&auto=format&fit=crop&q=80"
        };
        String plotImage = "https://images.unsplash.com/photo-1500382017468-9049fed747ef?w=600&auto=format&fit=crop&q=80";

        String[] builders = {
            "Prestige Group", "Lodha Group", "DLF Limited", "Brigade Group", 
            "Sobha Limited", "Godrej Properties", "Casagrand Builders", 
            "Aparna Constructions", "K Raheja Corp", "Kolte Patil"
        };

        int imgIndex = 0;
        int builderIndex = 0;

        for (Map.Entry<String, List<String>> entry : cityMap.entrySet()) {
            String city = entry.getKey();
            List<String> sublocations = entry.getValue();

            for (String sub : sublocations) {
                // Generate 4 properties per sublocation

                // Property 1: Affordable 2 BHK Flat
                String b1 = builders[builderIndex++ % builders.length];
                list.add(new PropertyBuilder()
                        .title(b1 + " " + sub + " Heights")
                        .city(city).sublocation(sub)
                        .builder(b1).type(PropertyType.FLAT).price(7500000.0).bhk(2).area(1100.0)
                        .possession("Ready to Move").description("A premium modern 2 BHK smart apartment close to IT hubs, public transit, and supermarkets.")
                        .amenities("Pool, Gym, Kids Area, Power Backup, 24/7 Security").lat(20.0).lng(75.0)
                        .img(flatImages[imgIndex++ % flatImages.length])
                        .schools("DPS School, St. Josephs").hospitals("Max Health, Columbia Asia").metro(sub + " Metro")
                        .invRating(4.3).yield(4.0).history("2023:6200000,2024:6800000,2025:7200000,2026:7500000").rating(4.3)
                        .build());

                // Property 2: Luxury 3 BHK Flat
                String b2 = builders[builderIndex++ % builders.length];
                list.add(new PropertyBuilder()
                        .title(b2 + " " + sub + " Grandeur")
                        .city(city).sublocation(sub)
                        .builder(b2).type(PropertyType.FLAT).price(14500000.0).bhk(3).area(1650.0)
                        .possession("Under Construction").description("Spacious 3 BHK luxury residences designed with premium fittings, high-quality tiles, and ventilation.")
                        .amenities("Infinity Pool, Skylounge, Tennis Court, Yoga Deck, Library").lat(20.1).lng(75.1)
                        .img(flatImages[imgIndex++ % flatImages.length])
                        .schools("Greenwood High, NPS").hospitals("Fortis Healthcare, Apollo").metro(sub + " Metro Station")
                        .invRating(4.5).yield(3.8).history("2023:11800000,2024:12800000,2025:13900000,2026:14500000").rating(4.6)
                        .build());

                // Property 3: Stately 4 BHK House/Villa
                String b3 = builders[builderIndex++ % builders.length];
                list.add(new PropertyBuilder()
                        .title(b3 + " " + sub + " Palms Villa")
                        .city(city).sublocation(sub)
                        .builder(b3).type(PropertyType.HOUSE).price(38000000.0).bhk(4).area(3200.0)
                        .possession("Ready to Move").description("Gorgeous architectural independent villa featuring private garden lawns, glass decks, and terrace view.")
                        .amenities("Private Garden, Private Gym, Home Theatre, Swimming Pool").lat(20.2).lng(75.2)
                        .img(villaImages[imgIndex % villaImages.length])
                        .schools("Cambridge Academy, Ryan International").hospitals("Manipal Hospital, Wockhardt").metro(sub + " Junction")
                        .invRating(4.7).yield(3.1).history("2023:32000000,2024:34500000,2025:36800000,2026:38000000").rating(4.8)
                        .build());

                // Property 4: Residential Land Plot
                String b4 = builders[builderIndex++ % builders.length];
                list.add(new PropertyBuilder()
                        .title(b4 + " " + sub + " Gated Plots")
                        .city(city).sublocation(sub)
                        .builder(b4).type(PropertyType.PLOT).price(9500000.0).bhk(0).area(2400.0)
                        .possession("Ready to Move").description("A ready-to-construct gated land plot equipped with individual water and electricity connections.")
                        .amenities("Gated Community, Tar Roads, Street Lights, Gated Entrance").lat(20.3).lng(75.3)
                        .img(plotImage)
                        .schools("Orchids International, DAV Public").hospitals("Narayana Multispeciality").metro(sub + " Bus Stand")
                        .invRating(4.4).yield(1.2).history("2023:7500000,2024:8200000,2025:8900000,2026:9500000").rating(4.1)
                        .build());
            }
        }

        return list;
    }

    private static class PropertyBuilder {
        private String title;
        private String city;
        private String sublocation;
        private String builder;
        private PropertyType type;
        private Double price;
        private Integer bhk;
        private Double area;
        private String possession;
        private String description;
        private String amenities;
        private Double lat;
        private Double lng;
        private String img;
        private String schools;
        private String hospitals;
        private String metro;
        private Double invRating;
        private Double yield;
        private String history;
        private Double rating;

        public PropertyBuilder title(String title) { this.title = title; return this; }
        public PropertyBuilder city(String city) { this.city = city; return this; }
        public PropertyBuilder sublocation(String sublocation) { this.sublocation = sublocation; return this; }
        public PropertyBuilder builder(String builder) { this.builder = builder; return this; }
        public PropertyBuilder type(PropertyType type) { this.type = type; return this; }
        public PropertyBuilder price(Double price) { this.price = price; return this; }
        public PropertyBuilder bhk(Integer bhk) { this.bhk = bhk; return this; }
        public PropertyBuilder area(Double area) { this.area = area; return this; }
        public PropertyBuilder possession(String possession) { this.possession = possession; return this; }
        public PropertyBuilder description(String description) { this.description = description; return this; }
        public PropertyBuilder amenities(String amenities) { this.amenities = amenities; return this; }
        public PropertyBuilder lat(Double lat) { this.lat = lat; return this; }
        public PropertyBuilder lng(Double lng) { this.lng = lng; return this; }
        public PropertyBuilder img(String img) { this.img = img; return this; }
        public PropertyBuilder schools(String schools) { this.schools = schools; return this; }
        public PropertyBuilder hospitals(String hospitals) { this.hospitals = hospitals; return this; }
        public PropertyBuilder metro(String metro) { this.metro = metro; return this; }
        public PropertyBuilder invRating(Double invRating) { this.invRating = invRating; return this; }
        public PropertyBuilder yield(Double yield) { this.yield = yield; return this; }
        public PropertyBuilder history(String history) { this.history = history; return this; }
        public PropertyBuilder rating(Double rating) { this.rating = rating; return this; }

        public Property build() {
            Property p = new Property();
            p.setTitle(title);
            p.setLocation(sublocation + ", " + city);
            p.setCity(city);
            p.setSublocation(sublocation);
            p.setBuilder(builder);
            p.setPropertyType(type);
            p.setPrice(price);
            p.setBhk(bhk);
            p.setArea(area);
            p.setPossessionStatus(possession);
            p.setDescription(description);
            p.setAmenities(amenities);
            p.setLatitude(lat);
            p.setLongitude(lng);
            p.setCoverImageUrl(img);
            p.setNearbySchools(schools);
            p.setNearbyHospitals(hospitals);
            p.setNearbyMetro(metro);
            p.setInvestmentRating(invRating);
            p.setRentalYield(yield);
            p.setPriceHistory(history);
            p.setRating(rating);
            p.setCreatedAt(LocalDateTime.now());
            return p;
        }
    }
}
