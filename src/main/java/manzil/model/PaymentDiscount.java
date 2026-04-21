package manzil.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Positive;
import lombok.Data;


@Entity
@Data
public class PaymentDiscount extends DiscountOffer{
    
    @Positive
    @Column
    private double cashbackAmount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CardScheme cardScheme;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CardType cardType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentPartner paymentPartner;

}
