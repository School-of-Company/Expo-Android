package com.school_of_company.data.di

import com.school_of_company.data.repository.admin.AdminRepository
import com.school_of_company.data.repository.admin.AdminRepositoryImpl
import com.school_of_company.data.repository.attendance.AttendanceRepository
import com.school_of_company.data.repository.attendance.AttendanceRepositoryImpl
import com.school_of_company.data.repository.auth.AuthRepository
import com.school_of_company.data.repository.auth.AuthRepositoryImpl
import com.school_of_company.data.repository.expo.ExpoRepository
import com.school_of_company.data.repository.expo.ExpoRepositoryImpl
import com.school_of_company.data.repository.form.FormRepository
import com.school_of_company.data.repository.form.FormRepositoryImpl
import com.school_of_company.data.repository.image.ImageRepository
import com.school_of_company.data.repository.image.ImageRepositoryImpl
import com.school_of_company.data.repository.participant.ParticipantRepository
import com.school_of_company.data.repository.participant.ParticipantRepositoryImpl
import com.school_of_company.data.repository.sms.SmsRepository
import com.school_of_company.data.repository.sms.SmsRepositoryImpl
import com.school_of_company.data.repository.standard.StandardRepository
import com.school_of_company.data.repository.standard.StandardRepositoryImpl
import com.school_of_company.data.repository.trainee.TraineeRepository
import com.school_of_company.data.repository.trainee.TraineeRepositoryImpl
import com.school_of_company.data.repository.training.TrainingRepository
import com.school_of_company.data.repository.training.TrainingRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ) : AuthRepository

    @Binds
    abstract fun bindSmsRepository(
        smsRepositoryImpl: SmsRepositoryImpl
    ) : SmsRepository

    @Binds
    abstract fun bindExpoRepository(
        expoRepositoryImpl: ExpoRepositoryImpl
    ) : ExpoRepository

    @Binds
    abstract fun bindImageRepository(
        imageRepositoryImpl: ImageRepositoryImpl
    ) : ImageRepository

    @Binds
    abstract fun bindTrainingRepository(
        trainingRepositoryImpl: TrainingRepositoryImpl
    ) : TrainingRepository

    @Binds
    abstract fun bindStandardRepository(
        standardRepositoryImpl: StandardRepositoryImpl
    ) : StandardRepository

    @Binds
    abstract fun bindAttendanceRepository(
        attendanceRepositoryImpl: AttendanceRepositoryImpl
    ) : AttendanceRepository

    @Binds
    abstract fun bindAdminRepository(
        adminRepositoryImpl: AdminRepositoryImpl
    ) : AdminRepository

    @Binds
    abstract fun bindTraineeRepository(
        traineeRepositoryImpl: TraineeRepositoryImpl
    ) : TraineeRepository

    @Binds
    abstract fun bindParticipantRepository(
        participantRepositoryImpl: ParticipantRepositoryImpl
    ) : ParticipantRepository

    @Binds
    abstract fun bindFormRepository(
        formRepositoryImpl: FormRepositoryImpl
    ) : FormRepository
}