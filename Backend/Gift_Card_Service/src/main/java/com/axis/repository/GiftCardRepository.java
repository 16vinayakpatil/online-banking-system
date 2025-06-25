package com.axis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.axis.entity.GiftCard;

@Repository
public interface GiftCardRepository extends JpaRepository<GiftCard, Integer> {

	@Query("SELECT g FROM GiftCard g WHERE g.account.accid = :accid")
	public List<GiftCard> findGiftCardsByAccId(int accid);
	
}
