package com.amplifyframework.datastore.generated.model;

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

/** This is an auto generated class representing the Order type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Orders", authRules = {
  @AuthRule(allow = AuthStrategy.OWNER, ownerField = "owner", identityClaim = "cognito:username", provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
public final class Order implements Model {
  public static final QueryField ID = field("Order", "id");
  public static final QueryField ORDER_TYPE = field("Order", "orderType");
  public static final QueryField ESTIMATED_TIME_COMPLETE = field("Order", "estimatedTimeComplete");
  public static final QueryField ORDER_TOTAL = field("Order", "orderTotal");
  public static final QueryField ORDER_ITEMS = field("Order", "orderItems");
  public static final QueryField IS_EDITABLE = field("Order", "isEditable");
  public static final QueryField IS_ACTIVE = field("Order", "isActive");
  public static final QueryField ORDER_RESTAURANT_ID = field("Order", "orderRestaurantId");
  public static final QueryField ORDER_DATE_TIME = field("Order", "orderDateTime");
  public static final QueryField ORDER_RESTAURANT = field("Order", "orderRestaurant");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String orderType;
  private final @ModelField(targetType="AWSTime", isRequired = true) Temporal.Time estimatedTimeComplete;
  private final @ModelField(targetType="Float", isRequired = true) Double orderTotal;
  private final @ModelField(targetType="AWSJSON", isRequired = true) String orderItems;
  private final @ModelField(targetType="Boolean", isRequired = true) Boolean isEditable;
  private final @ModelField(targetType="Boolean", isRequired = true) Boolean isActive;
  private final @ModelField(targetType="String", isRequired = true) String orderRestaurantId;
  private final @ModelField(targetType="AWSDateTime", isRequired = true) Temporal.DateTime orderDateTime;
  private final @ModelField(targetType="String", isRequired = true) String orderRestaurant;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getOrderType() {
      return orderType;
  }
  
  public Temporal.Time getEstimatedTimeComplete() {
      return estimatedTimeComplete;
  }
  
  public Double getOrderTotal() {
      return orderTotal;
  }
  
  public String getOrderItems() {
      return orderItems;
  }
  
  public Boolean getIsEditable() {
      return isEditable;
  }
  
  public Boolean getIsActive() {
      return isActive;
  }
  
  public String getOrderRestaurantId() {
      return orderRestaurantId;
  }
  
  public Temporal.DateTime getOrderDateTime() {
      return orderDateTime;
  }
  
  public String getOrderRestaurant() {
      return orderRestaurant;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Order(String id, String orderType, Temporal.Time estimatedTimeComplete, Double orderTotal, String orderItems, Boolean isEditable, Boolean isActive, String orderRestaurantId, Temporal.DateTime orderDateTime, String orderRestaurant) {
    this.id = id;
    this.orderType = orderType;
    this.estimatedTimeComplete = estimatedTimeComplete;
    this.orderTotal = orderTotal;
    this.orderItems = orderItems;
    this.isEditable = isEditable;
    this.isActive = isActive;
    this.orderRestaurantId = orderRestaurantId;
    this.orderDateTime = orderDateTime;
    this.orderRestaurant = orderRestaurant;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Order order = (Order) obj;
      return ObjectsCompat.equals(getId(), order.getId()) &&
              ObjectsCompat.equals(getOrderType(), order.getOrderType()) &&
              ObjectsCompat.equals(getEstimatedTimeComplete(), order.getEstimatedTimeComplete()) &&
              ObjectsCompat.equals(getOrderTotal(), order.getOrderTotal()) &&
              ObjectsCompat.equals(getOrderItems(), order.getOrderItems()) &&
              ObjectsCompat.equals(getIsEditable(), order.getIsEditable()) &&
              ObjectsCompat.equals(getIsActive(), order.getIsActive()) &&
              ObjectsCompat.equals(getOrderRestaurantId(), order.getOrderRestaurantId()) &&
              ObjectsCompat.equals(getOrderDateTime(), order.getOrderDateTime()) &&
              ObjectsCompat.equals(getOrderRestaurant(), order.getOrderRestaurant()) &&
              ObjectsCompat.equals(getCreatedAt(), order.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), order.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getOrderType())
      .append(getEstimatedTimeComplete())
      .append(getOrderTotal())
      .append(getOrderItems())
      .append(getIsEditable())
      .append(getIsActive())
      .append(getOrderRestaurantId())
      .append(getOrderDateTime())
      .append(getOrderRestaurant())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Order {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("orderType=" + String.valueOf(getOrderType()) + ", ")
      .append("estimatedTimeComplete=" + String.valueOf(getEstimatedTimeComplete()) + ", ")
      .append("orderTotal=" + String.valueOf(getOrderTotal()) + ", ")
      .append("orderItems=" + String.valueOf(getOrderItems()) + ", ")
      .append("isEditable=" + String.valueOf(getIsEditable()) + ", ")
      .append("isActive=" + String.valueOf(getIsActive()) + ", ")
      .append("orderRestaurantId=" + String.valueOf(getOrderRestaurantId()) + ", ")
      .append("orderDateTime=" + String.valueOf(getOrderDateTime()) + ", ")
      .append("orderRestaurant=" + String.valueOf(getOrderRestaurant()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static OrderTypeStep builder() {
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
  public static Order justId(String id) {
    return new Order(
      id,
      null,
      null,
      null,
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
      orderType,
      estimatedTimeComplete,
      orderTotal,
      orderItems,
      isEditable,
      isActive,
      orderRestaurantId,
      orderDateTime,
      orderRestaurant);
  }
  public interface OrderTypeStep {
    EstimatedTimeCompleteStep orderType(String orderType);
  }
  

  public interface EstimatedTimeCompleteStep {
    OrderTotalStep estimatedTimeComplete(Temporal.Time estimatedTimeComplete);
  }
  

  public interface OrderTotalStep {
    OrderItemsStep orderTotal(Double orderTotal);
  }
  

  public interface OrderItemsStep {
    IsEditableStep orderItems(String orderItems);
  }
  

  public interface IsEditableStep {
    IsActiveStep isEditable(Boolean isEditable);
  }
  

  public interface IsActiveStep {
    OrderRestaurantIdStep isActive(Boolean isActive);
  }
  

  public interface OrderRestaurantIdStep {
    OrderDateTimeStep orderRestaurantId(String orderRestaurantId);
  }
  

  public interface OrderDateTimeStep {
    OrderRestaurantStep orderDateTime(Temporal.DateTime orderDateTime);
  }
  

  public interface OrderRestaurantStep {
    BuildStep orderRestaurant(String orderRestaurant);
  }
  

  public interface BuildStep {
    Order build();
    BuildStep id(String id);
  }
  

  public static class Builder implements OrderTypeStep, EstimatedTimeCompleteStep, OrderTotalStep, OrderItemsStep, IsEditableStep, IsActiveStep, OrderRestaurantIdStep, OrderDateTimeStep, OrderRestaurantStep, BuildStep {
    private String id;
    private String orderType;
    private Temporal.Time estimatedTimeComplete;
    private Double orderTotal;
    private String orderItems;
    private Boolean isEditable;
    private Boolean isActive;
    private String orderRestaurantId;
    private Temporal.DateTime orderDateTime;
    private String orderRestaurant;
    @Override
     public Order build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Order(
          id,
          orderType,
          estimatedTimeComplete,
          orderTotal,
          orderItems,
          isEditable,
          isActive,
          orderRestaurantId,
          orderDateTime,
          orderRestaurant);
    }
    
    @Override
     public EstimatedTimeCompleteStep orderType(String orderType) {
        Objects.requireNonNull(orderType);
        this.orderType = orderType;
        return this;
    }
    
    @Override
     public OrderTotalStep estimatedTimeComplete(Temporal.Time estimatedTimeComplete) {
        Objects.requireNonNull(estimatedTimeComplete);
        this.estimatedTimeComplete = estimatedTimeComplete;
        return this;
    }
    
    @Override
     public OrderItemsStep orderTotal(Double orderTotal) {
        Objects.requireNonNull(orderTotal);
        this.orderTotal = orderTotal;
        return this;
    }
    
    @Override
     public IsEditableStep orderItems(String orderItems) {
        Objects.requireNonNull(orderItems);
        this.orderItems = orderItems;
        return this;
    }
    
    @Override
     public IsActiveStep isEditable(Boolean isEditable) {
        Objects.requireNonNull(isEditable);
        this.isEditable = isEditable;
        return this;
    }
    
    @Override
     public OrderRestaurantIdStep isActive(Boolean isActive) {
        Objects.requireNonNull(isActive);
        this.isActive = isActive;
        return this;
    }
    
    @Override
     public OrderDateTimeStep orderRestaurantId(String orderRestaurantId) {
        Objects.requireNonNull(orderRestaurantId);
        this.orderRestaurantId = orderRestaurantId;
        return this;
    }
    
    @Override
     public OrderRestaurantStep orderDateTime(Temporal.DateTime orderDateTime) {
        Objects.requireNonNull(orderDateTime);
        this.orderDateTime = orderDateTime;
        return this;
    }
    
    @Override
     public BuildStep orderRestaurant(String orderRestaurant) {
        Objects.requireNonNull(orderRestaurant);
        this.orderRestaurant = orderRestaurant;
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
    private CopyOfBuilder(String id, String orderType, Temporal.Time estimatedTimeComplete, Double orderTotal, String orderItems, Boolean isEditable, Boolean isActive, String orderRestaurantId, Temporal.DateTime orderDateTime, String orderRestaurant) {
      super.id(id);
      super.orderType(orderType)
        .estimatedTimeComplete(estimatedTimeComplete)
        .orderTotal(orderTotal)
        .orderItems(orderItems)
        .isEditable(isEditable)
        .isActive(isActive)
        .orderRestaurantId(orderRestaurantId)
        .orderDateTime(orderDateTime)
        .orderRestaurant(orderRestaurant);
    }
    
    @Override
     public CopyOfBuilder orderType(String orderType) {
      return (CopyOfBuilder) super.orderType(orderType);
    }
    
    @Override
     public CopyOfBuilder estimatedTimeComplete(Temporal.Time estimatedTimeComplete) {
      return (CopyOfBuilder) super.estimatedTimeComplete(estimatedTimeComplete);
    }
    
    @Override
     public CopyOfBuilder orderTotal(Double orderTotal) {
      return (CopyOfBuilder) super.orderTotal(orderTotal);
    }
    
    @Override
     public CopyOfBuilder orderItems(String orderItems) {
      return (CopyOfBuilder) super.orderItems(orderItems);
    }
    
    @Override
     public CopyOfBuilder isEditable(Boolean isEditable) {
      return (CopyOfBuilder) super.isEditable(isEditable);
    }
    
    @Override
     public CopyOfBuilder isActive(Boolean isActive) {
      return (CopyOfBuilder) super.isActive(isActive);
    }
    
    @Override
     public CopyOfBuilder orderRestaurantId(String orderRestaurantId) {
      return (CopyOfBuilder) super.orderRestaurantId(orderRestaurantId);
    }
    
    @Override
     public CopyOfBuilder orderDateTime(Temporal.DateTime orderDateTime) {
      return (CopyOfBuilder) super.orderDateTime(orderDateTime);
    }
    
    @Override
     public CopyOfBuilder orderRestaurant(String orderRestaurant) {
      return (CopyOfBuilder) super.orderRestaurant(orderRestaurant);
    }
  }
  
}
