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
  @AuthRule(allow = AuthStrategy.PUBLIC, operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ }),
  @AuthRule(allow = AuthStrategy.OWNER, ownerField = "owner", identityClaim = "cognito:username", provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
public final class Order implements Model {
  public static final QueryField ID = field("Order", "id");
  public static final QueryField ORDER_TYPE = field("Order", "orderType");
  public static final QueryField ESTIMATED_TIME_COMPLETE = field("Order", "estimatedTimeComplete");
  public static final QueryField ORDER_TOTAL = field("Order", "orderTotal");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String orderType;
  private final @ModelField(targetType="AWSTime", isRequired = true) Temporal.Time estimatedTimeComplete;
  private final @ModelField(targetType="Float", isRequired = true) Double orderTotal;
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
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Order(String id, String orderType, Temporal.Time estimatedTimeComplete, Double orderTotal) {
    this.id = id;
    this.orderType = orderType;
    this.estimatedTimeComplete = estimatedTimeComplete;
    this.orderTotal = orderTotal;
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
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      orderType,
      estimatedTimeComplete,
      orderTotal);
  }
  public interface OrderTypeStep {
    EstimatedTimeCompleteStep orderType(String orderType);
  }
  

  public interface EstimatedTimeCompleteStep {
    OrderTotalStep estimatedTimeComplete(Temporal.Time estimatedTimeComplete);
  }
  

  public interface OrderTotalStep {
    BuildStep orderTotal(Double orderTotal);
  }
  

  public interface BuildStep {
    Order build();
    BuildStep id(String id);
  }
  

  public static class Builder implements OrderTypeStep, EstimatedTimeCompleteStep, OrderTotalStep, BuildStep {
    private String id;
    private String orderType;
    private Temporal.Time estimatedTimeComplete;
    private Double orderTotal;
    @Override
     public Order build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Order(
          id,
          orderType,
          estimatedTimeComplete,
          orderTotal);
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
     public BuildStep orderTotal(Double orderTotal) {
        Objects.requireNonNull(orderTotal);
        this.orderTotal = orderTotal;
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
    private CopyOfBuilder(String id, String orderType, Temporal.Time estimatedTimeComplete, Double orderTotal) {
      super.id(id);
      super.orderType(orderType)
        .estimatedTimeComplete(estimatedTimeComplete)
        .orderTotal(orderTotal);
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
  }
  
}
