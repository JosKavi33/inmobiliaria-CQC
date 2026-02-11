import { createClient } from "@supabase/supabase-js";

const SUPABASE_URL = "https://kxyushotafmxiygjimvl.supabase.co";
const SUPABASE_ANON_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Imt4eXVzaG90YWZteGl5Z2ppbXZsIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NzAyNDI0MDIsImV4cCI6MjA4NTgxODQwMn0.tkNRx-rWeT5YS1Q6XhUlzP4liWcW53WgFYBJ06raI_4";

export const supabase = createClient(SUPABASE_URL, SUPABASE_ANON_KEY);
