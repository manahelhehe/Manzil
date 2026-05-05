package manzil.controller;

import manzil.exceptions.ResourceNotFoundException;
import manzil.model.CardScheme;
import manzil.model.CardType;
import manzil.model.PaymentDiscount;
import manzil.model.PaymentPartner;
import manzil.service.PaymentDiscountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/discounts/payment")
public class PaymentDiscountController
{
    private final PaymentDiscountService service;

    public PaymentDiscountController(PaymentDiscountService service)
    {
        this.service = service;
    }

    // ── GET ──────────────────────────────────────────────────────────────────

    @GetMapping
    public ResponseEntity<List<PaymentDiscount>> getAllPaymentDiscounts()
    {
        return ResponseEntity.ok(service.fetchAllPaymentDiscounts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDiscount> getPaymentDiscountById(@PathVariable long id)
    {
        Optional<PaymentDiscount> result = service.fetchPaymentDiscountById(id);
        return result.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/filter/partner")
    public ResponseEntity<List<PaymentDiscount>> getByPaymentPartner(@RequestParam PaymentPartner paymentPartner)
    {
        return ResponseEntity.ok(service.fetchByPaymentPartner(paymentPartner));
    }

    @GetMapping("/filter/card-scheme")
    public ResponseEntity<List<PaymentDiscount>> getByCardScheme(@RequestParam CardScheme cardScheme)
    {
        return ResponseEntity.ok(service.fetchByCardScheme(cardScheme));
    }

    @GetMapping("/filter/card-type")
    public ResponseEntity<List<PaymentDiscount>> getByCardType(@RequestParam CardType cardType)
    {
        return ResponseEntity.ok(service.fetchByCardType(cardType));
    }

    @GetMapping("/filter/cashback")
    public ResponseEntity<List<PaymentDiscount>> getByMinCashback(@RequestParam double min)
    {
        return ResponseEntity.ok(service.fetchByMinCashback(min));
    }

    // ── POST ─────────────────────────────────────────────────────────────────

    @PostMapping
    public ResponseEntity<PaymentDiscount> postPaymentDiscount(@RequestBody PaymentDiscount paymentDiscount)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.postPaymentDiscount(paymentDiscount));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<PaymentDiscount>> postPaymentDiscountList(@RequestBody List<PaymentDiscount> paymentDiscounts)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.postPaymentDiscountList(paymentDiscounts));
    }

    // ── PATCH ────────────────────────────────────────────────────────────────

    @PatchMapping("/{id}")
    public ResponseEntity<?> updatePaymentDiscount(@PathVariable long id, @RequestBody PaymentDiscount updatedPaymentDiscount)
    {
        try
        {
            Optional<PaymentDiscount> result = service.updatePaymentDiscount(id, updatedPaymentDiscount);
            return result.map(ResponseEntity::ok)
                         .orElse(ResponseEntity.notFound().build());
        }
        catch (ResourceNotFoundException e)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // ── DELETE ───────────────────────────────────────────────────────────────

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePaymentDiscount(@PathVariable long id)
    {
        Optional<String> result = service.dropPaymentDiscount(id);
        return result.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }
}
