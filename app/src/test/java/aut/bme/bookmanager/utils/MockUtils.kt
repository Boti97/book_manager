package aut.bme.bookmanager.utils

import org.mockito.ArgumentCaptor
import org.mockito.MockSettings
import org.mockito.Mockito

inline fun <reified T : Any> mock(): T = Mockito.mock(T::class.java)!!

inline fun <reified T : Any> mock(s: MockSettings): T = Mockito.mock(T::class.java, s)!!

inline fun <reified T : Any> argumentCaptor(): ArgumentCaptor<T> =
    ArgumentCaptor.forClass(T::class.java)

inline fun <reified T : Any> nullableArgumentCaptor(): ArgumentCaptor<T?> =
    ArgumentCaptor.forClass(T::class.java)

inline fun <reified T : Any> capture(captor: ArgumentCaptor<T>): T = captor.capture()