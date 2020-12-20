package com.example.androidacademy.data


import com.example.androidacademy.R
import com.example.androidacademy.model.Movie

class Database_movies {
    fun getMovies(): List<Movie> {
         return listOf(
                 Movie(
                     R.drawable.movie_avengers_end_game,
                         "13+",
                     R.drawable.ic_like,
                         "Action, Adventure, Drama",
                         4f,
                         "125 Reviews",
                         "Avengers: End Game",
                         "137 min",
                     "After the devastating events " +
                     "of Avengers: Infinity War, the universe is in ruins. With the help of remaining allies, " +
                             "the Avengers assemble once more in order to reverse Thanos' actions and restore balance to the universe.",
                     R.drawable.details_movie_avenger_end_game

                 ),
                 Movie(R.drawable.movie_tenet,
                         "16+",
                     R.drawable.ic_like_red,
                         "Action, Sci-Fi, Thriller",
                         5f,
                         "98 Reviews",
                         "Tenet",
                         "97 min",
                     "After the devastating events " +
                             "of Avengers: Infinity War, the universe is in ruins. With the help of remaining allies, " +
                             "the Avengers assemble once more in order to reverse Thanos' actions and restore balance to the universe.",
                     R.drawable.details_movie_avenger_end_game
                 ),
                 Movie(R.drawable.movie_black_widow,
                         "13+",
                     R.drawable.ic_like,
                         "Action, Adventure, Sci-Fi",
                         4f,
                         "38 Reviews",
                         "Black Widow",
                         "102 min",
                     "After the devastating events " +
                             "of Avengers: Infinity War, the universe is in ruins. With the help of remaining allies, " +
                             "the Avengers assemble once more in order to reverse Thanos' actions and restore balance to the universe.",
                     R.drawable.details_movie_avenger_end_game
                 ),
                 Movie(R.drawable.movie_wonder_woman_1984,
                         "13+",
                     R.drawable.ic_like,
                         "Action, Adventure, Fantasy",
                         5f,
                         "74 Reviews",
                         "Wonder Woman 1984",
                         "120 min",
                     "After the devastating events " +
                             "of Avengers: Infinity War, the universe is in ruins. With the help of remaining allies, " +
                             "the Avengers assemble once more in order to reverse Thanos' actions and restore balance to the universe.",
                     R.drawable.details_movie_avenger_end_game

                 ),

         )
    }


}