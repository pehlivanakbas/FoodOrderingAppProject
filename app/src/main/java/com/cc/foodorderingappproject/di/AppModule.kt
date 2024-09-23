package com.cc.foodorderingappproject.di

import com.cc.foodorderingappproject.datasource.SepetYemeklerDataSource
import com.cc.foodorderingappproject.datasource.YemeklerDataSource
import com.cc.foodorderingappproject.repo.SepetYemeklerRepository
import com.cc.foodorderingappproject.repo.YemeklerRepository
import com.cc.foodorderingappproject.retrofit.ApiUtils
import com.cc.foodorderingappproject.retrofit.ApiUtilsSepet
import com.cc.foodorderingappproject.retrofit.SepetYemeklerDaoInterface
import com.cc.foodorderingappproject.retrofit.YemeklerDaoInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideYemeklerRepository(yds: YemeklerDataSource) : YemeklerRepository {
        return YemeklerRepository(yds)
    }

    @Provides
    @Singleton
    fun provideKisilerDataSource(ydao: YemeklerDaoInterface) : YemeklerDataSource {
        return YemeklerDataSource(ydao)
    }
    @Provides
    @Singleton
    fun provideYemeklerDao():YemeklerDaoInterface{
        return ApiUtils.getYemeklerInterface()
    }
    @Provides
    @Singleton
    fun provideSepetYemeklerRepository(syds: SepetYemeklerDataSource) : SepetYemeklerRepository {
        return SepetYemeklerRepository(syds)
    }

    @Provides
    @Singleton
    fun provideSepetYemeklerDataSource(sydao: SepetYemeklerDaoInterface) : SepetYemeklerDataSource {
        return SepetYemeklerDataSource(sydao)
    }

    @Provides
    @Singleton
    fun provideSepetYemeklerDao() : SepetYemeklerDaoInterface {
        return ApiUtilsSepet.getSepetYemeklerDao()
    }}