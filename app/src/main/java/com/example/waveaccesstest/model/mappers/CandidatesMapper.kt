package com.example.waveaccesstest.model.mappers

import com.example.waveaccesstest.model.domain.EyeColor
import com.example.waveaccesstest.model.domain.Fruit
import com.example.waveaccesstest.model.data.Candidate as CandidateData
import com.example.waveaccesstest.model.cache.Candidate as CandidateCache
import com.example.waveaccesstest.model.domain.Candidate as CandidateDomain

class CandidatesMapper: Mapper<CandidateData, CandidateCache, CandidateDomain>() {
    override fun dataToCache(data: CandidateData): CandidateCache {
        TODO("Not yet implemented")
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
            registered = data.registered,
            latitude = data.latitude,
            longitude = data.longitude,
            friends = data.friends,
            favoriteFruit = Fruit.getFruitByName(data.favoriteFruit)
        )
    }

    override fun cacheToDomain(cache: CandidateCache): CandidateDomain {
        TODO("Not yet implemented")
    }

    override fun domainToCache(domain: CandidateDomain): CandidateCache {
        TODO("Not yet implemented")
    }
}