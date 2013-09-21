package org.urlMonitor.model;

import java.io.*;
import java.util.*;

import javax.persistence.*;

import lombok.*;

/**
 * @author Alex Eng <a href="mailto:aeng@redhat.com">aeng@redhat.com</a>
 */
@MappedSuperclass
@Getter
@Setter
public class ModelBase implements Serializable
{
   @Id
   @GeneratedValue
   @NonNull
   protected Long id;

   @Temporal(TemporalType.TIMESTAMP)
   @NonNull
   protected Date creationDate;

   @Temporal(TemporalType.TIMESTAMP)
   @NonNull
   protected Date lastChanged;

   @PrePersist
   private void onPersist()
   {
      Date now = new Date();
      if (creationDate == null)
      {
         creationDate = now;
      }
      if (lastChanged == null)
      {
         lastChanged = now;
      }
   }

   @PreUpdate
   private void onUpdate()
   {
      lastChanged = new Date();
   }
}
