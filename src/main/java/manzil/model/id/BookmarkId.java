package manzil.model.id;

import lombok.Data;

import java.io.Serializable;

@Data
public class BookmarkId implements Serializable
{
    private long user;
    private long place;
}
