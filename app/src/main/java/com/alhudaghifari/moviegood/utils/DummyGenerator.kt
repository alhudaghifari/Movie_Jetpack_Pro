package com.alhudaghifari.moviegood.utils

import com.alhudaghifari.moviegood.data.local.entity.MovieEntity
import com.alhudaghifari.moviegood.data.local.entity.TvEntity

object DummyGenerator {

    fun generateDummyMovies(): List<MovieEntity> {
        val movies = ArrayList<MovieEntity>()

        movies.add(
            MovieEntity("1", "The Tomorrow War", "July 1, 2021", "Action, Science Fiction",82.0,
                "The world is stunned when a group of time travelers arrive from the year 2051 to deliver an urgent message: Thirty years in the future, mankind is losing a global war against a deadly alien species. The only hope for survival is for soldiers and civilians from the present to be transported to the future and join the fight. Among those recruited is high school teacher and family man Dan Forester. Determined to save the world for his young daughter, Dan teams up with a brilliant scientist and his estranged father in a desperate quest to rewrite the fate of the planet.",
                "Chris McKay", "https://www.themoviedb.org/t/p/w1280/34nDCQZwaEvsy4CFO5hkGRFDCVU.jpg")
        )
        movies.add(
            MovieEntity("2", "Peter Rabbit 2: The Runaway", "June 18, 2021", "Family, Comedy, Adventure, Animation, Fantasy",73.0,
                "Bea, Thomas, and the rabbits have created a makeshift family, but despite his best efforts, Peter can’t seem to shake his mischievous reputation. Adventuring out of the garden, Peter finds himself in a world where his mischief is appreciated, but when his family risks everything to come looking for him, Peter must figure out what kind of bunny he wants to be.",
                "Will Gluck", "https://www.themoviedb.org/t/p/w1280/cycDz68DtTjJrDJ1fV8EBq2Xdpb.jpg")
        )
        movies.add(
            MovieEntity("3","Jungle Cruise", "July 30, 2021", "Adventure, Fantasy, Comedy, Action", 83.0,
                "Dr. Lily Houghton enlists the aid of wisecracking skipper Frank Wolff to take her down the Amazon in his dilapidated boat. Together, they search for an ancient tree that holds the power to heal – a discovery that will change the future of medicine.",
                "Jaume Collet-Serra", "https://www.themoviedb.org/t/p/w1280/9dKCd55IuTT5QRs989m9Qlb7d2B.jpg")
        )
        movies.add(
            MovieEntity("4", "Space Jam: A New Legacy", "July 16, 2021", "Animation, Comedy, Family, Science Fiction", 77.0,
                "When LeBron and his young son Dom are trapped in a digital space by a rogue A.I., LeBron must get them home safe by leading Bugs, Lola Bunny and the whole gang of notoriously undisciplined Looney Tunes to victory over the A.I.'s digitized champions on the court. It's Tunes versus Goons in the highest-stakes challenge of his life.",
                "Malcolm D. Lee", "https://www.themoviedb.org/t/p/w1280/5bFK5d3mVTAvBCXi5NPWH0tYjKl.jpg")
        )
        movies.add(
            MovieEntity("5","F9","June 16, 2021","Action, Crime, Thriller",79.0,
                "Dominic Toretto and his crew battle the most skilled assassin and high-performance driver they've ever encountered: his forsaken brother.",
                "Justin Lin","https://www.themoviedb.org/t/p/w1280/bOFaAXmWWXC3Rbv4u4uM9ZSzRXP.jpg")
        )
        movies.add(
            MovieEntity("6","The Forever Purge","July 2, 2021","Adventure, Thriller, Action, Horror, Western",78.0,
                "All the rules are broken as a sect of lawless marauders decides that the annual Purge does not stop at daybreak and instead should never end as they chase a group of immigrants who they want to punish because of their harsh historical past.",
                "Everardo Gout","https://www.themoviedb.org/t/p/w1280/uHA5COgDzcxjpYSHHulrKVl6ByL.jpg")
        )
        movies.add(
            MovieEntity("7","The Boss Baby","June 2, 2021","Animation, Comedy, Adventure, Family",79.0,
                "The Templeton brothers — Tim and his Boss Baby little bro Ted — have become adults and drifted away from each other. But a new boss baby with a cutting-edge approach and a can-do attitude is about to bring them together again … and inspire a new family business.",
                "Tom McGrath","https://www.themoviedb.org/t/p/w1280/5dExO5G2iaaTxYnLIFKLWofDzyI.jpg")
        )
        movies.add(
            MovieEntity("8","Luca","June 17, 2021","Animation, Comedy, Family, Fantasy",81.0,
                "Luca and his best friend Alberto experience an unforgettable summer on the Italian Riviera. But all the fun is threatened by a deeply-held secret: they are sea monsters from another world just below the water’s surface.",
                "Enrico Casarosa","https://www.themoviedb.org/t/p/w1280/jTswp6KyDYKtvC52GbHagrZbGvD.jpg")
        )
        movies.add(
            MovieEntity("9","Infinite","June 10, 2021","Science Fiction, Action, Thriller",72.0,
                "Evan McCauley has skills he never learned and memories of places he has never visited. Self-medicated and on the brink of a mental breakdown, a secret group that call themselves “Infinites” come to his rescue, revealing that his memories are real.",
                "Antoine Fuqua","https://www.themoviedb.org/t/p/w1280/niw2AKHz6XmwiRMLWaoyAOAti0G.jpg")
        )
        movies.add(
            MovieEntity("10","Dynasty Warriors","April 29, 2021","Action, Adventure, Family",66.0,
                "Warlords, warriors and statesmen wage a battle for supremacy in this fantasy tale based on the hit video games and the \"Romance of the Three Kingdoms.\"",
                "Roy Chow Hin-Yeung","https://www.themoviedb.org/t/p/w1280/7BCTdek5LFHglcgl7shsm7igJAH.jpg")
        )
        movies.add(
            MovieEntity("11","Trollhunters: Rise of the Titans","July 21, 2021","Animation, Fantasy, Family,, Action, Adventure, Science Fiction",80.0,
                "Arcadia may look like an ordinary town, but it lies at the center of magical and mystical lines that makes it a nexus for many battles among otherworldly creatures, including trolls, aliens, and wizards. Now, various heroes will team-up in an epic adventure where they must fight the Arcane Order for control over the magic that binds them all together.",
                "Francisco Ruiz Velasco","https://www.themoviedb.org/t/p/w1280/zvUNFeTz0Sssb210wSiIiHRjA4W.jpg")
        )
        movies.add(
            MovieEntity("12","Wrath of Man","May 7, 2021","Crime, Action, Thriller",79.0,
                "A cold and mysterious new security guard for a Los Angeles cash truck company surprises his co-workers when he unleashes precision skills during a heist. The crew is left wondering who he is and where he came from. Soon, the marksman's ultimate motive becomes clear as he takes dramatic and irrevocable steps to settle a score.",
                "Guy Ritchie","https://www.themoviedb.org/t/p/w1280/M7SUK85sKjaStg4TKhlAVyGlz3.jpg")
        )

        return movies
    }

    fun generateRemoteDummyMovies(): List<MovieEntity> {
        val movies = ArrayList<MovieEntity>()

        movies.add(
            MovieEntity("1", "The Tomorrow War", "July 1, 2021", "Action, Science Fiction",82.0,
                "The world is stunned when a group of time travelers arrive from the year 2051 to deliver an urgent message: Thirty years in the future, mankind is losing a global war against a deadly alien species. The only hope for survival is for soldiers and civilians from the present to be transported to the future and join the fight. Among those recruited is high school teacher and family man Dan Forester. Determined to save the world for his young daughter, Dan teams up with a brilliant scientist and his estranged father in a desperate quest to rewrite the fate of the planet.",
                "Chris McKay", "https://www.themoviedb.org/t/p/w1280/34nDCQZwaEvsy4CFO5hkGRFDCVU.jpg")
        )
        movies.add(
            MovieEntity("2", "Peter Rabbit 2: The Runaway", "June 18, 2021", "Family, Comedy, Adventure, Animation, Fantasy",73.0,
                "Bea, Thomas, and the rabbits have created a makeshift family, but despite his best efforts, Peter can’t seem to shake his mischievous reputation. Adventuring out of the garden, Peter finds himself in a world where his mischief is appreciated, but when his family risks everything to come looking for him, Peter must figure out what kind of bunny he wants to be.",
                "Will Gluck", "https://www.themoviedb.org/t/p/w1280/cycDz68DtTjJrDJ1fV8EBq2Xdpb.jpg")
        )
        movies.add(
            MovieEntity("3","Jungle Cruise", "July 30, 2021", "Adventure, Fantasy, Comedy, Action", 83.0,
                "Dr. Lily Houghton enlists the aid of wisecracking skipper Frank Wolff to take her down the Amazon in his dilapidated boat. Together, they search for an ancient tree that holds the power to heal – a discovery that will change the future of medicine.",
                "Jaume Collet-Serra", "https://www.themoviedb.org/t/p/w1280/9dKCd55IuTT5QRs989m9Qlb7d2B.jpg")
        )
        movies.add(
            MovieEntity("4", "Space Jam: A New Legacy", "July 16, 2021", "Animation, Comedy, Family, Science Fiction", 77.0,
                "When LeBron and his young son Dom are trapped in a digital space by a rogue A.I., LeBron must get them home safe by leading Bugs, Lola Bunny and the whole gang of notoriously undisciplined Looney Tunes to victory over the A.I.'s digitized champions on the court. It's Tunes versus Goons in the highest-stakes challenge of his life.",
                "Malcolm D. Lee", "https://www.themoviedb.org/t/p/w1280/5bFK5d3mVTAvBCXi5NPWH0tYjKl.jpg")
        )
        movies.add(
            MovieEntity("5","F9","June 16, 2021","Action, Crime, Thriller",79.0,
                "Dominic Toretto and his crew battle the most skilled assassin and high-performance driver they've ever encountered: his forsaken brother.",
                "Justin Lin","https://www.themoviedb.org/t/p/w1280/bOFaAXmWWXC3Rbv4u4uM9ZSzRXP.jpg")
        )
        movies.add(
            MovieEntity("6","The Forever Purge","July 2, 2021","Adventure, Thriller, Action, Horror, Western",78.0,
                "All the rules are broken as a sect of lawless marauders decides that the annual Purge does not stop at daybreak and instead should never end as they chase a group of immigrants who they want to punish because of their harsh historical past.",
                "Everardo Gout","https://www.themoviedb.org/t/p/w1280/uHA5COgDzcxjpYSHHulrKVl6ByL.jpg")
        )
        movies.add(
            MovieEntity("7","The Boss Baby","June 2, 2021","Animation, Comedy, Adventure, Family",79.0,
                "The Templeton brothers — Tim and his Boss Baby little bro Ted — have become adults and drifted away from each other. But a new boss baby with a cutting-edge approach and a can-do attitude is about to bring them together again … and inspire a new family business.",
                "Tom McGrath","https://www.themoviedb.org/t/p/w1280/5dExO5G2iaaTxYnLIFKLWofDzyI.jpg")
        )
        movies.add(
            MovieEntity("8","Luca","June 17, 2021","Animation, Comedy, Family, Fantasy",81.0,
                "Luca and his best friend Alberto experience an unforgettable summer on the Italian Riviera. But all the fun is threatened by a deeply-held secret: they are sea monsters from another world just below the water’s surface.",
                "Enrico Casarosa","https://www.themoviedb.org/t/p/w1280/jTswp6KyDYKtvC52GbHagrZbGvD.jpg")
        )
        movies.add(
            MovieEntity("9","Infinite","June 10, 2021","Science Fiction, Action, Thriller",72.0,
                "Evan McCauley has skills he never learned and memories of places he has never visited. Self-medicated and on the brink of a mental breakdown, a secret group that call themselves “Infinites” come to his rescue, revealing that his memories are real.",
                "Antoine Fuqua","https://www.themoviedb.org/t/p/w1280/niw2AKHz6XmwiRMLWaoyAOAti0G.jpg")
        )
        movies.add(
            MovieEntity("10","Dynasty Warriors","April 29, 2021","Action, Adventure, Family",66.0,
                "Warlords, warriors and statesmen wage a battle for supremacy in this fantasy tale based on the hit video games and the \"Romance of the Three Kingdoms.\"",
                "Roy Chow Hin-Yeung","https://www.themoviedb.org/t/p/w1280/7BCTdek5LFHglcgl7shsm7igJAH.jpg")
        )
        movies.add(
            MovieEntity("11","Trollhunters: Rise of the Titans","July 21, 2021","Animation, Fantasy, Family,, Action, Adventure, Science Fiction",80.0,
                "Arcadia may look like an ordinary town, but it lies at the center of magical and mystical lines that makes it a nexus for many battles among otherworldly creatures, including trolls, aliens, and wizards. Now, various heroes will team-up in an epic adventure where they must fight the Arcane Order for control over the magic that binds them all together.",
                "Francisco Ruiz Velasco","https://www.themoviedb.org/t/p/w1280/zvUNFeTz0Sssb210wSiIiHRjA4W.jpg")
        )
        movies.add(
            MovieEntity("12","Wrath of Man","May 7, 2021","Crime, Action, Thriller",79.0,
                "A cold and mysterious new security guard for a Los Angeles cash truck company surprises his co-workers when he unleashes precision skills during a heist. The crew is left wondering who he is and where he came from. Soon, the marksman's ultimate motive becomes clear as he takes dramatic and irrevocable steps to settle a score.",
                "Guy Ritchie","https://www.themoviedb.org/t/p/w1280/M7SUK85sKjaStg4TKhlAVyGlz3.jpg")
        )

        return movies
    }

    fun generateDummyTvShows() : List<TvEntity> {
        val movies = ArrayList<TvEntity>()

        movies.add(
            TvEntity("1", "Rick and Morty", "2013", "Animation, Comedy, Sci-Fi & Fantasy, Action & Adventure",88.0,
                "Rick is a mentally-unbalanced but scientifically-gifted old man who has recently reconnected with his family. He spends most of his time involving his young grandson Morty in dangerous, outlandish adventures throughout space and alternate universes. Compounded with Morty's already unstable family life, these events cause Morty much distress at home and school.",
                "Dan Harmon, Justin ROiland", "https://www.themoviedb.org/t/p/w1280/8kOWDBK6XlPUzckuHDo3wwVRFwt.jpg")
        )
        movies.add(
            TvEntity("2", "Loki", "2021", "Drama, Sci-Fi & Fantasy\n",82.0,
                "After stealing the Tesseract during the events of “Avengers: Endgame,” an alternate version of Loki is brought to the mysterious Time Variance Authority, a bureaucratic organization that exists outside of time and space and monitors the timeline. They give Loki a choice: face being erased from existence due to being a “time variant” or help fix the timeline and stop a greater threat.",
                "Michael Waldron", "https://www.themoviedb.org/t/p/w1280/kEl2t3OhXc3Zb9FBh1AuYzRTgZp.jpg")
        )
        movies.add(
            TvEntity("3","The Simpsons", "1989", "Family, Animation, Comedy", 79.0,
                "Set in Springfield, the average American town, the show focuses on the antics and everyday adventures of the Simpson family; Homer, Marge, Bart, Lisa and Maggie, as well as a virtual cast of thousands. Since the beginning, the series has been a pop culture icon, attracting hundreds of celebrities to guest star. The show has also made name for itself in its fearless satirical take on politics, media and American life in general.",
                "Matt Groening", "https://www.themoviedb.org/t/p/w1280/zLudbPueg8CxGhMS2tyDh3p0TdK.jpg")
        )
        movies.add(
            TvEntity("4", "The Flash", "2014", "Drama, Sci-Fi & Fantasy", 77.0,
                "After a particle accelerator causes a freak storm, CSI Investigator Barry Allen is struck by lightning and falls into a coma. Months later he awakens with the power of super speed, granting him the ability to move through Central City like an unseen guardian angel. Though initially excited by his newfound powers, Barry is shocked to discover he is not the only \"meta-human\" who was created in the wake of the accelerator explosion -- and not everyone is using their new powers for good. Barry partners with S.T.A.R. Labs and dedicates his life to protect the innocent. For now, only a few close friends and associates know that Barry is literally the fastest man alive, but it won't be long before the world learns what Barry Allen has become...The Flash.",
                "Greg Berlanti, Geoff Johns, Andres Kreisberg", "https://www.themoviedb.org/t/p/w1280/lJA2RCMfsWoskqlQhXPSLFQGXEJ.jpg")
        )
        movies.add(
            TvEntity("5","The Good Doctor","2017","Drama",86.0,
                "A young surgeon with Savant syndrome is recruited into the surgical unit of a prestigious hospital. The question will arise: can a person who doesn't have the ability to relate to people actually save their lives.",
                "David Shore","https://www.themoviedb.org/t/p/w1280/6tfT03sGp9k4c0J3dypjrI8TSAI.jpg")
        )
        movies.add(
            TvEntity("6","Sucré Salé","2002","Talk",93.0,
                "Talk Show on TV.",
                "James","https://www.themoviedb.org/t/p/w1280/cPvXh1UmjZY93Kck3WFW55mb1km.jpg")
        )
        movies.add(
            TvEntity("7","Monsters at Work","2021","Family, Comedy, Animation",72.0,
                "Ever since he was a kid, Tylor Tuskmon has dreamed of becoming a Scarer just like his idol James P. Sullivan, and now that dream is about to come true... or not. The day he arrives at Monsters Incorporated to begin his dream job as a Scarer, he learns that scaring is out and laughter is in! After being reassigned to the Monsters, Inc. Facilities Team, Tylor sets his sights on a new goal: figuring out how to be funny and becoming a Jokester.",
                "Roberts Gannaway","https://www.themoviedb.org/t/p/w1280/2gxgwhcuSmI5xtexb0t9zGj43FS.jpg")
        )
        movies.add(
            TvEntity("8","Game of Thrones","2011","Sci-Fi & Fantasy, Drama, Action & Adventure",84.0,
                "Seven noble families fight for control of the mythical land of Westeros. Friction between the houses leads to full-scale war. All while a very ancient evil awakens in the farthest north. Amidst the war, a neglected military order of misfits, the Night's Watch, is all that stands between the realms of men and icy horrors beyond.",
                "David Benioff, D. B. Weiss","https://www.themoviedb.org/t/p/w1280/u3bZgnGQ9T01sWNhyveQz0wH0Hl.jpg")
        )
        movies.add(
            TvEntity("9","Miraculous: Tales of Ladybug & Cat Noir","2015","Action & Adventure, Animation, Kids",79.0,
                "Normal high school kids by day, protectors of Paris by night! Miraculous follows the heroic adventures of Marinette and Adrien as they transform into Ladybug and Cat Noir and set out to capture akumas, creatures responsible for turning the people of Paris into villains. But neither hero knows the other’s true identity – or that they’re classmates!",
                "Thomas Astruc","https://www.themoviedb.org/t/p/w1280/qxbW47zmgFyBVmZSIqD9NA1DEjT.jpg")
        )
        movies.add(
            TvEntity("10","De Slimste Mens","2012","Talk",66.0,
                "A quizshow presented by Philip Freriks and one-man jury Maarten van Rossum.",
                "Bambang Sikomo","https://www.themoviedb.org/t/p/w1280/8ZGSF0GZHNTmZvZfTLmjg4BF91E.jpg")
        )
        movies.add(
            TvEntity("11","The Vampire Diaries","2009","Drama, Sci-Fi & Fantasy",83.0,
                "The story of two vampire brothers obsessed with the same girl, who bears a striking resemblance to the beautiful but ruthless vampire they knew and loved in 1864.",
                "Kevin Williamson","https://www.themoviedb.org/t/p/w1280/kLEha9zVVv8acGFKTX4gjvSR2Q0.jpg")
        )
        movies.add(
            TvEntity("12","The Falcon and the Winter Soldier","2021","Sci-Fi & Fantasy, Action & Adventure, Drama, War & Politics",78.0,
                "Following the events of “Avengers: Endgame”, the Falcon, Sam Wilson and the Winter Soldier, Bucky Barnes team up in a global adventure that tests their abilities, and their patience.",
                "Malcolm Spellman","https://www.themoviedb.org/t/p/w1280/6kbAMLteGO8yyewYau6bJ683sw7.jpg")
        )

        return movies
    }

    fun generateRemoteDummyTvShows() : List<TvEntity> {
        val movies = ArrayList<TvEntity>()

        movies.add(
            TvEntity("1", "Rick and Morty", "2013", "Animation, Comedy, Sci-Fi & Fantasy, Action & Adventure",88.0,
                "Rick is a mentally-unbalanced but scientifically-gifted old man who has recently reconnected with his family. He spends most of his time involving his young grandson Morty in dangerous, outlandish adventures throughout space and alternate universes. Compounded with Morty's already unstable family life, these events cause Morty much distress at home and school.",
                "Dan Harmon, Justin ROiland", "https://www.themoviedb.org/t/p/w1280/8kOWDBK6XlPUzckuHDo3wwVRFwt.jpg")
        )
        movies.add(
            TvEntity("2", "Loki", "2021", "Drama, Sci-Fi & Fantasy\n",82.0,
                "After stealing the Tesseract during the events of “Avengers: Endgame,” an alternate version of Loki is brought to the mysterious Time Variance Authority, a bureaucratic organization that exists outside of time and space and monitors the timeline. They give Loki a choice: face being erased from existence due to being a “time variant” or help fix the timeline and stop a greater threat.",
                "Michael Waldron", "https://www.themoviedb.org/t/p/w1280/kEl2t3OhXc3Zb9FBh1AuYzRTgZp.jpg")
        )
        movies.add(
            TvEntity("3","The Simpsons", "1989", "Family, Animation, Comedy", 79.0,
                "Set in Springfield, the average American town, the show focuses on the antics and everyday adventures of the Simpson family; Homer, Marge, Bart, Lisa and Maggie, as well as a virtual cast of thousands. Since the beginning, the series has been a pop culture icon, attracting hundreds of celebrities to guest star. The show has also made name for itself in its fearless satirical take on politics, media and American life in general.",
                "Matt Groening", "https://www.themoviedb.org/t/p/w1280/zLudbPueg8CxGhMS2tyDh3p0TdK.jpg")
        )
        movies.add(
            TvEntity("4", "The Flash", "2014", "Drama, Sci-Fi & Fantasy", 77.0,
                "After a particle accelerator causes a freak storm, CSI Investigator Barry Allen is struck by lightning and falls into a coma. Months later he awakens with the power of super speed, granting him the ability to move through Central City like an unseen guardian angel. Though initially excited by his newfound powers, Barry is shocked to discover he is not the only \"meta-human\" who was created in the wake of the accelerator explosion -- and not everyone is using their new powers for good. Barry partners with S.T.A.R. Labs and dedicates his life to protect the innocent. For now, only a few close friends and associates know that Barry is literally the fastest man alive, but it won't be long before the world learns what Barry Allen has become...The Flash.",
                "Greg Berlanti, Geoff Johns, Andres Kreisberg", "https://www.themoviedb.org/t/p/w1280/lJA2RCMfsWoskqlQhXPSLFQGXEJ.jpg")
        )
        movies.add(
            TvEntity("5","The Good Doctor","2017","Drama",86.0,
                "A young surgeon with Savant syndrome is recruited into the surgical unit of a prestigious hospital. The question will arise: can a person who doesn't have the ability to relate to people actually save their lives.",
                "David Shore","https://www.themoviedb.org/t/p/w1280/6tfT03sGp9k4c0J3dypjrI8TSAI.jpg")
        )
        movies.add(
            TvEntity("6","Sucré Salé","2002","Talk",93.0,
                "Talk Show on TV.",
                "James","https://www.themoviedb.org/t/p/w1280/cPvXh1UmjZY93Kck3WFW55mb1km.jpg")
        )
        movies.add(
            TvEntity("7","Monsters at Work","2021","Family, Comedy, Animation",72.0,
                "Ever since he was a kid, Tylor Tuskmon has dreamed of becoming a Scarer just like his idol James P. Sullivan, and now that dream is about to come true... or not. The day he arrives at Monsters Incorporated to begin his dream job as a Scarer, he learns that scaring is out and laughter is in! After being reassigned to the Monsters, Inc. Facilities Team, Tylor sets his sights on a new goal: figuring out how to be funny and becoming a Jokester.",
                "Roberts Gannaway","https://www.themoviedb.org/t/p/w1280/2gxgwhcuSmI5xtexb0t9zGj43FS.jpg")
        )
        movies.add(
            TvEntity("8","Game of Thrones","2011","Sci-Fi & Fantasy, Drama, Action & Adventure",84.0,
                "Seven noble families fight for control of the mythical land of Westeros. Friction between the houses leads to full-scale war. All while a very ancient evil awakens in the farthest north. Amidst the war, a neglected military order of misfits, the Night's Watch, is all that stands between the realms of men and icy horrors beyond.",
                "David Benioff, D. B. Weiss","https://www.themoviedb.org/t/p/w1280/u3bZgnGQ9T01sWNhyveQz0wH0Hl.jpg")
        )
        movies.add(
            TvEntity("9","Miraculous: Tales of Ladybug & Cat Noir","2015","Action & Adventure, Animation, Kids",79.0,
                "Normal high school kids by day, protectors of Paris by night! Miraculous follows the heroic adventures of Marinette and Adrien as they transform into Ladybug and Cat Noir and set out to capture akumas, creatures responsible for turning the people of Paris into villains. But neither hero knows the other’s true identity – or that they’re classmates!",
                "Thomas Astruc","https://www.themoviedb.org/t/p/w1280/qxbW47zmgFyBVmZSIqD9NA1DEjT.jpg")
        )
        movies.add(
            TvEntity("10","De Slimste Mens","2012","Talk",66.0,
                "A quizshow presented by Philip Freriks and one-man jury Maarten van Rossum.",
                "Bambang Sikomo","https://www.themoviedb.org/t/p/w1280/8ZGSF0GZHNTmZvZfTLmjg4BF91E.jpg")
        )
        movies.add(
            TvEntity("11","The Vampire Diaries","2009","Drama, Sci-Fi & Fantasy",83.0,
                "The story of two vampire brothers obsessed with the same girl, who bears a striking resemblance to the beautiful but ruthless vampire they knew and loved in 1864.",
                "Kevin Williamson","https://www.themoviedb.org/t/p/w1280/kLEha9zVVv8acGFKTX4gjvSR2Q0.jpg")
        )
        movies.add(
            TvEntity("12","The Falcon and the Winter Soldier","2021","Sci-Fi & Fantasy, Action & Adventure, Drama, War & Politics",78.0,
                "Following the events of “Avengers: Endgame”, the Falcon, Sam Wilson and the Winter Soldier, Bucky Barnes team up in a global adventure that tests their abilities, and their patience.",
                "Malcolm Spellman","https://www.themoviedb.org/t/p/w1280/6kbAMLteGO8yyewYau6bJ683sw7.jpg")
        )

        return movies
    }

}