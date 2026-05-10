package manzil.service;

import manzil.exceptions.ResourceNotFoundException;
import manzil.model.Vibe;
import manzil.repository.VibeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VibeService
{
    @Autowired
    private VibeRepository repo;

    public Vibe fetchVibeById(int id)
    {
        Vibe v = repo.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Vibe Not Found (ID: " + id + ")"));
        return v;
    }
}
