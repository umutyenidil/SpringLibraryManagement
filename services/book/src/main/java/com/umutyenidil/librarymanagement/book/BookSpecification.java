package com.umutyenidil.librarymanagement.book;

import com.umutyenidil.librarymanagement.author.Author;
import com.umutyenidil.librarymanagement.category.Category;
import com.umutyenidil.librarymanagement.genre.Genre;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class BookSpecification {

    public static Specification<Book> build(BookSearchRequest request) {

        return (root, query, cb) -> {

            // arama kriterleri icin liste olustur
            List<Predicate> predicates = new ArrayList<>();

            // silinmemis kitaplari al
            predicates.add(cb.isNull(root.get("deletedAt")));

            // request icinde q parametresi gelmis mi kontrol et
            if (StringUtils.hasText(request.q())) {

                // isim ve aciklama alanlarini birlestir ve gelen q parametresi ile hepsi icin like ile arama yap
                Expression<String> fullText = cb.concat(
                        cb.lower(root.get("name")),
                        cb.concat(" ", cb.lower(root.get("description")))
                );
                predicates.add(cb.like(fullText, "%" + request.q().toLowerCase() + "%"));
            }

            // dil id parametresi geldiyse arama kriterleri listesine ekle
            if (request.languageId() != null) {
                predicates.add(cb.equal(root.get("language").get("id"), request.languageId()));
            }

            // yayin evi parametresi geldiyse arama kriterlerine ekle
            if (request.publisherId() != null) {
                predicates.add(cb.equal(root.get("publisher").get("id"), request.publisherId()));
            }

            // tur id'leri geldiyse genres tablosu ile join yapip id'lerle iliskili kitaplarin getirilmesini sagla
            if (request.genreIds() != null && !request.genreIds().isEmpty()) {
                Join<Book, Genre> genreJoin = root.join("genres");
                predicates.add(genreJoin.get("id").in(request.genreIds()));
            }

            // kategori id'leri geldiyse categories tablosu ile join yapip id'lerle iliskili kitaplarin getirilmesini sagla
            if (request.categoryIds() != null && !request.categoryIds().isEmpty()) {
                Join<Book, Category> catJoin = root.join("categories");
                predicates.add(catJoin.get("id").in(request.categoryIds()));
            }

            // yazar id'leri geldiyse authors tablosu ile join yapip id'lerle iliskili kitaplarin getirilmesini sagla
            if (request.authorIds() != null && !request.authorIds().isEmpty()) {
                Join<Book, Author> authorJoin = root.join("authors");
                predicates.add(authorJoin.get("id").in(request.authorIds()));
            }

            // kitle belirtilmisse sorguya ekle
            if (request.audience() != null) {
                predicates.add(cb.equal(root.get("audience"), request.audience()));
            }

            // format belirtilmisse sorguya ekle
            if (request.format() != null) {
                predicates.add(cb.equal(root.get("format"), request.format()));
            }

            // yayin tarihi from ve end degerleri belirtildiyse sorguya ekle
            if (request.publishDateFrom() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("publishDate"), request.publishDateFrom()));
            }
            if (request.publishDateTo() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("publishDate"), request.publishDateTo()));
            }

            // coklu joinlerde olusabilecek coklanacak satirlarin getirilesini engellemek icin distinct veri filtresi uygula
            if (query != null) {
                query.distinct(true);
            }

            // olusturulan tum filtre/sorgulari and ile birlestir
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
