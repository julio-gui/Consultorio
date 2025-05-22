package com.example.consultorio

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

object SupabaseClient {
    val supabase = createSupabaseClient(
        supabaseUrl = "https://frglsjvzmkxzcfyntvsi.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImZyZ2xzanZ6bWt4emNmeW50dnNpIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDY3MDMxMzYsImV4cCI6MjA2MjI3OTEzNn0.k2qVjbDJXIvHNKNoDMYzglITuAKnsXgrN69MMDPFjv4"
    ) {
        install(Postgrest)
    }
}