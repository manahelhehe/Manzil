package manzil.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
public class PaymentDiscount extends DiscountOffer
{
    private double cashbackAmount;

    @Enumerated(EnumType.STRING)
    private CardScheme cardScheme;

    @Enumerated(EnumType.STRING)
    private CardType cardType;

    @Enumerated(EnumType.STRING)
    private PaymentPartner paymentPartner;

    public double getCashbackAmount() { 
        return cashbackAmount; }
    public void setCashbackAmount(double cashbackAmount) { 
        this.cashbackAmount = cashbackAmount; }




    public CardScheme getCardScheme() { 
        return cardScheme; }
    public void setCardScheme(CardScheme cardScheme) { 
        this.cardScheme = cardScheme; }




    public CardType getCardType() { 
        return cardType; }
    public void setCardType(CardType cardType) { 
        this.cardType = cardType; }



    public PaymentPartner getPaymentPartner() { 
        return paymentPartner; }
    public void setPaymentPartner(PaymentPartner paymentPartner) {
        this.paymentPartner = paymentPartner; }}
