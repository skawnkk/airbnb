package codesquad.team17.gnb.place.domain;

public class Place {
    private Long id;
    private String name;
    private String imageUrl;

    private Location location;
    private int price;
    private int maximumNumberOfPeople;
    private Option option;

    private double star;
    private int reviewCount;

    private Long hostId;
    private String description;

    public Place(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.imageUrl = builder.imageUrl;
        this.location = builder.location;
        this.price = builder.price;
        this.maximumNumberOfPeople = builder.maximumNumberOfPeople;
        this.option = builder.option;
        this.star = builder.star;
        this.reviewCount = builder.reviewCount;
        this.hostId = builder.hostId;
        this.description = builder.description;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Location getLocation() {
        return location;
    }

    public int getPrice() {
        return price;
    }

    public int getMaximumNumberOfPeople() {
        return maximumNumberOfPeople;
    }

    public Option getOption() {
        return option;
    }

    public double getStar() {
        return star;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public Long getHostId() {
        return hostId;
    }

    public String getDescription() {
        return description;
    }

    public static class Builder {
        private Long id;
        private String name;
        private String imageUrl;

        private Location location;
        private Option option;

        private double star;
        private int reviewCount;

        private Long hostId;
        private int maximumNumberOfPeople;
        private String description;
        private int price;

        public Builder() {
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Builder location(Location location) {
            this.location = location;
            return this;
        }

        public Builder option(Option option) {
            this.option = option;
            return this;
        }

        public Builder star(double star) {
            this.star = star;
            return this;
        }

        public Builder reviewCount(int reviewCount) {
            this.reviewCount = reviewCount;
            return this;
        }

        public Builder hostId(Long hostId) {
            this.hostId = hostId;
            return this;
        }

        public Builder maximumNumberOfPeople(int maximumNumberOfPeople) {
            this.maximumNumberOfPeople = maximumNumberOfPeople;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder price(int price) {
            this.price = price;
            return this;
        }

        public Place build() {
            return new Place(this);
        }
    }
}
