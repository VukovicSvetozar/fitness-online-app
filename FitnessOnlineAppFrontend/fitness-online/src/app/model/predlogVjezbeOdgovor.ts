export class PredlogVjezbeOdgovor {

  name: string;
  difficulty: string;
  instructions: string

  constructor(
    name: string,
    difficulty: string,
    instructions: string
  ) {
    this.name = name;
    this.difficulty = difficulty;
    this.instructions = instructions
  }

}