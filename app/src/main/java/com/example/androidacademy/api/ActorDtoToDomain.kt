package com.example.androidacademy.api


import com.example.androidacademy.BuildConfig
import com.example.androidacademy.data.Actor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun convertActorDtoToDomain(actorsDto: List<ActorDto>): List<Actor> =
    withContext(Dispatchers.Default) {
        actorsDto.map { actorDto ->
            Actor(
                id = actorDto.id,
                name = actorDto.name,
                picture = actorDto.image?.let { BuildConfig.BASE_IMAGE_URL + actorDto.image }
            )
        }
    }