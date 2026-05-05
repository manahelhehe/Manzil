package manzil.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import manzil.model.CardScheme;
import manzil.model.CardType;
import manzil.model.PaymentDiscount;
import manzil.model.PaymentPartner;

@Repository
public interface PaymentDiscountRepository extends JpaRepository<PaymentDiscount, Long>
{
    List<PaymentDiscount> findByPaymentPartner(PaymentPartner paymentPartner);
    List<PaymentDiscount> findByCardScheme(CardScheme cardScheme);
    List<PaymentDiscount> findByCardType(CardType cardType);
    List<PaymentDiscount> findByCashbackAmountGreaterThanEqual(double amount);
}
