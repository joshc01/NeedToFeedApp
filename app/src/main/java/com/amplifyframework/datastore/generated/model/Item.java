package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.annotations.BelongsTo;
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

/** This is an auto generated class representing the Item type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Items", authRules = {
  @AuthRule(allow = AuthStrategy.PUBLIC, operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
@Index(name = "byRestaurant", fields = {"restaurantID"})
public final class Item implements Model {
  public static final QueryField ID = field("Item", "id");
  public static final QueryField TITLE = field("Item", "title");
  public static final QueryField RESTAURANT_ID = field("Item", "restaurantID");
  public static final QueryField RESTAURANT = field("Item", "itemRestaurantId");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String title;
  private final @ModelField(targetType="ID") String restaurantID;
  private final @ModelField(targetType="Restaurant") @BelongsTo(targetName = "itemRestaurantId", type = Restaurant.class) Restaurant restaurant;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getTitle() {
      return title;
  }
  
  public String getRestaurantId() {
      return restaurantID;
  }
  
  public Restaurant getRestaurant() {
      return restaurant;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Item(String id, String title, String restaurantID, Restaurant restaurant) {
    this.id = id;
    this.title = title;
    this.restaurantID = restaurantID;
    this.restaurant = restaurant;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Item item = (Item) obj;
      return ObjectsCompat.equals(getId(), item.getId()) &&
              ObjectsCompat.equals(getTitle(), item.getTitle()) &&
              ObjectsCompat.equals(getRestaurantId(), item.getRestaurantId()) &&
              ObjectsCompat.equals(getRestaurant(), item.getRestaurant()) &&
              ObjectsCompat.equals(getCreatedAt(), item.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), item.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getTitle())
      .append(getRestaurantId())
      .append(getRestaurant())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Item {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("title=" + String.valueOf(getTitle()) + ", ")
      .append("restaurantID=" + String.valueOf(getRestaurantId()) + ", ")
      .append("restaurant=" + String.valueOf(getRestaurant()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static TitleStep builder() {
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
  public static Item justId(String id) {
    return new Item(
      id,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      title,
      restaurantID,
      restaurant);
  }
  public interface TitleStep {
    BuildStep title(String title);
  }
  

  public interface BuildStep {
    Item build();
    BuildStep id(String id);
    BuildStep restaurantId(String restaurantId);
    BuildStep restaurant(Restaurant restaurant);
  }
  

  public static class Builder implements TitleStep, BuildStep {
    private String id;
    private String title;
    private String restaurantID;
    private Restaurant restaurant;
    @Override
     public Item build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Item(
          id,
          title,
          restaurantID,
          restaurant);
    }
    
    @Override
     public BuildStep title(String title) {
        Objects.requireNonNull(title);
        this.title = title;
        return this;
    }
    
    @Override
     public BuildStep restaurantId(String restaurantId) {
        this.restaurantID = restaurantId;
        return this;
    }
    
    @Override
     public BuildStep restaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
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
    private CopyOfBuilder(String id, String title, String restaurantId, Restaurant restaurant) {
      super.id(id);
      super.title(title)
        .restaurantId(restaurantId)
        .restaurant(restaurant);
    }
    
    @Override
     public CopyOfBuilder title(String title) {
      return (CopyOfBuilder) super.title(title);
    }
    
    @Override
     public CopyOfBuilder restaurantId(String restaurantId) {
      return (CopyOfBuilder) super.restaurantId(restaurantId);
    }
    
    @Override
     public CopyOfBuilder restaurant(Restaurant restaurant) {
      return (CopyOfBuilder) super.restaurant(restaurant);
    }
  }
  
}
