package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.annotations.HasMany;
import com.amplifyframework.core.model.temporal.Temporal;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.AuthStrategy;
import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.ModelOperation;
import com.amplifyframework.core.model.annotations.AuthRule;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the Restaurant type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Restaurants", authRules = {
  @AuthRule(allow = AuthStrategy.PUBLIC, operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
public final class Restaurant implements Model {
  public static final QueryField ID = field("Restaurant", "id");
  public static final QueryField NAME = field("Restaurant", "name");
  public static final QueryField CATEGORY = field("Restaurant", "category");
  public static final QueryField IMAGE = field("Restaurant", "image");
  public static final QueryField LOCATION = field("Restaurant", "location");
  public static final QueryField TIME_OPEN = field("Restaurant", "timeOpen");
  public static final QueryField TIME_CLOSE = field("Restaurant", "timeClose");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String name;
  private final @ModelField(targetType="Item") @HasMany(associatedWith = "restaurantID", type = Item.class) List<Item> items = null;
  private final @ModelField(targetType="String", isRequired = true) String category;
  private final @ModelField(targetType="AWSURL") String image;
  private final @ModelField(targetType="String", isRequired = true) String location;
  private final @ModelField(targetType="AWSTime", isRequired = true) Temporal.Time timeOpen;
  private final @ModelField(targetType="AWSTime", isRequired = true) Temporal.Time timeClose;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getName() {
      return name;
  }
  
  public List<Item> getItems() {
      return items;
  }
  
  public String getCategory() {
      return category;
  }
  
  public String getImage() {
      return image;
  }
  
  public String getLocation() {
      return location;
  }
  
  public Temporal.Time getTimeOpen() {
      return timeOpen;
  }
  
  public Temporal.Time getTimeClose() {
      return timeClose;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Restaurant(String id, String name, String category, String image, String location, Temporal.Time timeOpen, Temporal.Time timeClose) {
    this.id = id;
    this.name = name;
    this.category = category;
    this.image = image;
    this.location = location;
    this.timeOpen = timeOpen;
    this.timeClose = timeClose;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Restaurant restaurant = (Restaurant) obj;
      return ObjectsCompat.equals(getId(), restaurant.getId()) &&
              ObjectsCompat.equals(getName(), restaurant.getName()) &&
              ObjectsCompat.equals(getCategory(), restaurant.getCategory()) &&
              ObjectsCompat.equals(getImage(), restaurant.getImage()) &&
              ObjectsCompat.equals(getLocation(), restaurant.getLocation()) &&
              ObjectsCompat.equals(getTimeOpen(), restaurant.getTimeOpen()) &&
              ObjectsCompat.equals(getTimeClose(), restaurant.getTimeClose()) &&
              ObjectsCompat.equals(getCreatedAt(), restaurant.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), restaurant.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getName())
      .append(getCategory())
      .append(getImage())
      .append(getLocation())
      .append(getTimeOpen())
      .append(getTimeClose())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Restaurant {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("name=" + String.valueOf(getName()) + ", ")
      .append("category=" + String.valueOf(getCategory()) + ", ")
      .append("image=" + String.valueOf(getImage()) + ", ")
      .append("location=" + String.valueOf(getLocation()) + ", ")
      .append("timeOpen=" + String.valueOf(getTimeOpen()) + ", ")
      .append("timeClose=" + String.valueOf(getTimeClose()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static NameStep builder() {
      return new Builder();
  }
  
  /** 
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   */
  public static Restaurant justId(String id) {
    return new Restaurant(
      id,
      null,
      null,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      name,
      category,
      image,
      location,
      timeOpen,
      timeClose);
  }
  public interface NameStep {
    CategoryStep name(String name);
  }
  

  public interface CategoryStep {
    LocationStep category(String category);
  }
  

  public interface LocationStep {
    TimeOpenStep location(String location);
  }
  

  public interface TimeOpenStep {
    TimeCloseStep timeOpen(Temporal.Time timeOpen);
  }
  

  public interface TimeCloseStep {
    BuildStep timeClose(Temporal.Time timeClose);
  }
  

  public interface BuildStep {
    Restaurant build();
    BuildStep id(String id);
    BuildStep image(String image);
  }
  

  public static class Builder implements NameStep, CategoryStep, LocationStep, TimeOpenStep, TimeCloseStep, BuildStep {
    private String id;
    private String name;
    private String category;
    private String location;
    private Temporal.Time timeOpen;
    private Temporal.Time timeClose;
    private String image;
    @Override
     public Restaurant build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Restaurant(
          id,
          name,
          category,
          image,
          location,
          timeOpen,
          timeClose);
    }
    
    @Override
     public CategoryStep name(String name) {
        Objects.requireNonNull(name);
        this.name = name;
        return this;
    }
    
    @Override
     public LocationStep category(String category) {
        Objects.requireNonNull(category);
        this.category = category;
        return this;
    }
    
    @Override
     public TimeOpenStep location(String location) {
        Objects.requireNonNull(location);
        this.location = location;
        return this;
    }
    
    @Override
     public TimeCloseStep timeOpen(Temporal.Time timeOpen) {
        Objects.requireNonNull(timeOpen);
        this.timeOpen = timeOpen;
        return this;
    }
    
    @Override
     public BuildStep timeClose(Temporal.Time timeClose) {
        Objects.requireNonNull(timeClose);
        this.timeClose = timeClose;
        return this;
    }
    
    @Override
     public BuildStep image(String image) {
        this.image = image;
        return this;
    }
    
    /** 
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     */
    public BuildStep id(String id) {
        this.id = id;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, String name, String category, String image, String location, Temporal.Time timeOpen, Temporal.Time timeClose) {
      super.id(id);
      super.name(name)
        .category(category)
        .location(location)
        .timeOpen(timeOpen)
        .timeClose(timeClose)
        .image(image);
    }
    
    @Override
     public CopyOfBuilder name(String name) {
      return (CopyOfBuilder) super.name(name);
    }
    
    @Override
     public CopyOfBuilder category(String category) {
      return (CopyOfBuilder) super.category(category);
    }
    
    @Override
     public CopyOfBuilder location(String location) {
      return (CopyOfBuilder) super.location(location);
    }
    
    @Override
     public CopyOfBuilder timeOpen(Temporal.Time timeOpen) {
      return (CopyOfBuilder) super.timeOpen(timeOpen);
    }
    
    @Override
     public CopyOfBuilder timeClose(Temporal.Time timeClose) {
      return (CopyOfBuilder) super.timeClose(timeClose);
    }
    
    @Override
     public CopyOfBuilder image(String image) {
      return (CopyOfBuilder) super.image(image);
    }
  }
  
}
