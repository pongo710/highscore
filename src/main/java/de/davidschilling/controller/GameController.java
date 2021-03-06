package de.davidschilling.controller;

import de.davidschilling.game.Game;
import de.davidschilling.game.GameService;
import de.davidschilling.score.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * David Schilling - davejs92@gmail.com
 */
@Controller
@RequestMapping(value = "/games")
public class GameController {

    private GameService gameService;
    private ScoreService scoreService;

    @Autowired
    public GameController(GameService gameService, ScoreService scoreService) {
        this.gameService = gameService;
        this.scoreService = scoreService;
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String prepareGameCreation(Model model) {
        model.addAttribute("game", new Game());
        return "game/new";
    }
    @RequestMapping(value = "", method = RequestMethod.POST)
    public String createGame(Game game) {
        gameService.createGame(game);
        return "redirect:/games/" + game.getName();
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public String getGame(@PathVariable String name, Model model) {
        Game game = gameService.getGameByName(name);
        model.addAttribute("game", game);
        model.addAttribute("scores", scoreService.getScoreForGame(name));
        return "game/game";
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getAll(Model model) {
        model.addAttribute("games", gameService.getAllGamesWithHighscore());
        return "game/games";
    }
}
