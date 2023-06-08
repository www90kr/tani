package com.tani.demo.entity;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Review {
   private Integer reviewNo;
   private Integer Star;
   private String reviewContent;
   private String username;
   private LocalDate reviewDate;
   private Integer odtNo;
   private Integer orderNo;
   private Integer code;
   private String orderName;

   public Review addWriter(String username) {
      this.username = username;
      return this;
   }

}