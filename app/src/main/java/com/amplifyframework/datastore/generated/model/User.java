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

/** This is an auto generated class representing the User type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Users", authRules = {
  @AuthRule(allow = AuthStrategy.OWNER, ownerField = "owner", identityClaim = "cognito:username", provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
public final class User implements Model {
  public static final QueryField ID = field("User", "id");
  public static final QueryField PHONE_NUMBERS = field("User", "phoneNumbers");
  public static final QueryField ADDRESSES = field("User", "addresses");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String") List<String> phoneNumbers;
  private final @ModelField(targetType="AWSJSON") String addresses;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public List<String> getPhoneNumbers() {
      return phoneNumbers;
  }
  
  public String getAddresses() {
      return addresses;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private User(String id, List<String> phoneNumbers, String addresses) {
    this.id = id;
    this.phoneNumbers = phoneNumbers;
    this.addresses = addresses;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      User user = (User) obj;
      return ObjectsCompat.equals(getId(), user.getId()) &&
              ObjectsCompat.equals(getPhoneNumbers(), user.getPhoneNumbers()) &&
              ObjectsCompat.equals(getAddresses(), user.getAddresses()) &&
              ObjectsCompat.equals(getCreatedAt(), user.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), user.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getPhoneNumbers())
      .append(getAddresses())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("User {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("phoneNumbers=" + String.valueOf(getPhoneNumbers()) + ", ")
      .append("addresses=" + String.valueOf(getAddresses()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static BuildStep builder() {
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
  public static User justId(String id) {
    return new User(
      id,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      phoneNumbers,
      addresses);
  }
  public interface BuildStep {
    User build();
    BuildStep id(String id);
    BuildStep phoneNumbers(List<String> phoneNumbers);
    BuildStep addresses(String addresses);
  }
  

  public static class Builder implements BuildStep {
    private String id;
    private List<String> phoneNumbers;
    private String addresses;
    @Override
     public User build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new User(
          id,
          phoneNumbers,
          addresses);
    }
    
    @Override
     public BuildStep phoneNumbers(List<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
        return this;
    }
    
    @Override
     public BuildStep addresses(String addresses) {
        this.addresses = addresses;
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
    private CopyOfBuilder(String id, List<String> phoneNumbers, String addresses) {
      super.id(id);
      super.phoneNumbers(phoneNumbers)
        .addresses(addresses);
    }
    
    @Override
     public CopyOfBuilder phoneNumbers(List<String> phoneNumbers) {
      return (CopyOfBuilder) super.phoneNumbers(phoneNumbers);
    }
    
    @Override
     public CopyOfBuilder addresses(String addresses) {
      return (CopyOfBuilder) super.addresses(addresses);
    }
  }
  
}
