@Entity(tableName = "users")
@Serializable
data class User(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "user_name") val name: String
)

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    fun getAll(): List<User>

    @Insert
    fun insert(user: User)

    @Update
    fun update(user: User)

    @Delete
    fun delete(id: Int)
}

@Inject
class UserService

@Provides
fun provideConfig(): String = "config"