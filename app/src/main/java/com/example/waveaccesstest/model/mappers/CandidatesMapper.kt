package com.example.waveaccesstest.model.mappers

import com.example.waveaccesstest.model.domain.EyeColor
import com.example.waveaccesstest.model.domain.Fruit
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import com.example.waveaccesstest.model.data.Candidate as CandidateData
import com.example.waveaccesstest.model.cache.Candidate as CandidateCache
import com.example.waveaccesstest.model.domain.Candidate as CandidateDomain

class CandidatesMapper: Mapper<CandidateData, CandidateCache, CandidateDomain>() {
    override fun dataToCache(data: CandidateData): CandidateCache {
        return CandidateCache(
            id = data.id,
            isActive = data.isActive,
            age = data.age,
            eyeColor = data.eyeColor,
            name = data.name,
            company = data.company,
            email = data.email,
            phone = data.phone,
            address = data.address,
            about = data.about,
            registered = data.registered,
            latitude = data.latitude,
            longitude = data.longitude,
            friends = data.friends,
            favoriteFruit = data.favoriteFruit
        )
    }

    override fun dataToDomain(data: CandidateData): CandidateDomain {
        return CandidateDomain(
            id = data.id,
            isActive = data.isActive,
            age = data.age,
            eyeColor = EyeColor.getColorByName(data.eyeColor),
            name = data.name,
            company = data.company,
            email = data.email,
            phone = data.phone,
            address = data.address,
            about = data.about,
            registered = LocalDateTime.parse(data.registered),
            latitude = data.latitude,
            longitude = data.longitude,
            friends = data.friends,
            favoriteFruit = Fruit.getFruitByName(data.favoriteFruit)
        )
    }

    override fun cacheToDomain(cache: CandidateCache): CandidateDomain {
        return CandidateDomain(
            id = cache.id,
            isActive = cache.isActive,
            age = cache.age,
            eyeColor = EyeColor.getColorByName(cache.eyeColor),
            name = cache.name,
            company = cache.company,
            email = cache.email,
            phone = cache.phone,
            address = cache.address,
            about = cache.about,
            registered = LocalDateTime.parse(cache.registered),
            latitude = cache.latitude,
            longitude = cache.longitude,
            friends = cache.friends,
            favoriteFruit = Fruit.getFruitByName(cache.favoriteFruit)
        )
    }

    override fun domainToCache(domain: CandidateDomain): CandidateCache {
        return CandidateCache(
            id = domain.id,
            isActive = domain.isActive,
            age = domain.age,
            eyeColor = domain.eyeColor.name,
            name = domain.name,
            company = domain.company,
            email = domain.email,
            phone = domain.phone,
            address = domain.address,
            about = domain.about,
            registered = domain.registered.format(DateTimeFormatter.ofPattern("yyy-MM-dd'T'HH:mm:ss")),
            latitude = domain.latitude,
            longitude = domain.longitude,
            friends = domain.friends,
            favoriteFruit = domain.favoriteFruit.name
        )
    }
}