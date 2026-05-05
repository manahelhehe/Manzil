package manzil.service;

import jakarta.transaction.Transactional;
import manzil.exceptions.ResourceNotFoundException;
import manzil.model.CardScheme;
import manzil.model.CardType;
import manzil.model.PaymentDiscount;
import manzil.model.PaymentPartner;
import manzil.repository.PaymentDiscountRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentDiscountService
{
    private final PaymentDiscountRepository repo;

    public PaymentDiscountService(PaymentDiscountRepository repo)
    {
        this.repo = repo;
    }

    public List<PaymentDiscount> fetchAllPaymentDiscounts()
    {
        return repo.findAll();
    }

    public Optional<PaymentDiscount> fetchPaymentDiscountById(long id)
    {
        return repo.findById(id);
    }

    public List<PaymentDiscount> fetchByPaymentPartner(PaymentPartner paymentPartner)
    {
        return repo.findByPaymentPartner(paymentPartner);
    }

    public List<PaymentDiscount> fetchByCardScheme(CardScheme cardScheme)
    {
        return repo.findByCardScheme(cardScheme);
    }

    public List<PaymentDiscount> fetchByCardType(CardType cardType)
    {
        return repo.findByCardType(cardType);
    }

    public List<PaymentDiscount> fetchByMinCashback(double amount)
    {
        return repo.findByCashbackAmountGreaterThanEqual(amount);
    }

    public PaymentDiscount postPaymentDiscount(PaymentDiscount paymentDiscount)
    {
        return repo.save(paymentDiscount);
    }

    public List<PaymentDiscount> postPaymentDiscountList(List<PaymentDiscount> paymentDiscounts)
    {
        return repo.saveAll(paymentDiscounts);
    }

    @Transactional
    public Optional<PaymentDiscount> updatePaymentDiscount(long id, PaymentDiscount updatedPaymentDiscount) throws ResourceNotFoundException
    {
        Optional<PaymentDiscount> result = fetchPaymentDiscountById(id);

        if (result.isEmpty())
            return result;

        PaymentDiscount existing = result.get();

        // Parent fields
        if (updatedPaymentDiscount.getTitle() != null)
            existing.setTitle(updatedPaymentDiscount.getTitle());

        if (updatedPaymentDiscount.getDescription() != null)
            existing.setDescription(updatedPaymentDiscount.getDescription());

        if (updatedPaymentDiscount.getPercentage() != -1)
            existing.setPercentage(updatedPaymentDiscount.getPercentage());

        if (updatedPaymentDiscount.getMinSpend() != -1)
            existing.setMinSpend(updatedPaymentDiscount.getMinSpend());

        if (updatedPaymentDiscount.getValidFrom() != null)
            existing.setValidFrom(updatedPaymentDiscount.getValidFrom());

        if (updatedPaymentDiscount.getValidTo() != null)
            existing.setValidTo(updatedPaymentDiscount.getValidTo());

        if (updatedPaymentDiscount.getBranch() != null)
            existing.setBranch(updatedPaymentDiscount.getBranch());

        if (updatedPaymentDiscount.getStatus() != null)
            existing.setStatus(updatedPaymentDiscount.getStatus());

        // Subtype fields
        if (updatedPaymentDiscount.getPaymentPartner() != null)
            existing.setPaymentPartner(updatedPaymentDiscount.getPaymentPartner());

        if (updatedPaymentDiscount.getCardScheme() != null)
            existing.setCardScheme(updatedPaymentDiscount.getCardScheme());

        if (updatedPaymentDiscount.getCardType() != null)
            existing.setCardType(updatedPaymentDiscount.getCardType());

        if (updatedPaymentDiscount.getCashbackAmount() > 0)
            existing.setCashbackAmount(updatedPaymentDiscount.getCashbackAmount());

        return Optional.of(repo.save(existing));
    }

    public Optional<String> dropPaymentDiscount(long id)
    {
        Optional<PaymentDiscount> result = fetchPaymentDiscountById(id);

        if (result.isEmpty())
            return Optional.empty();

        repo.delete(result.get());
        return Optional.of("Payment Discount Deleted Successfully (ID: " + id + ")");
    }
}
