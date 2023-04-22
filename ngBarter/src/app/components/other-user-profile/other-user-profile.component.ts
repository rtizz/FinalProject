import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from 'src/app/models/user';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-other-user-profile',
  templateUrl: './other-user-profile.component.html',
  styleUrls: ['./other-user-profile.component.css']
})
export class OtherUserProfileComponent implements OnInit {

  user: User = new User();
  loggedInUser: User = new User();

  constructor(private route: ActivatedRoute, private userService: UserService) { }



ngOnInit() {
  const userId = this.route.snapshot.params['id'];
  this.userService.getUserById(userId).subscribe({
    next: (user: User) => {
      this.user = user;
    },
    error: (nojoy) => {
      console.log(nojoy);
    }
  });
}

addFriend() {
  this.userService.addFriend( this.user.id).subscribe({
    next: (response) => {
      console.log(response);

    },
    error: (nojoy) => {
      console.log(nojoy);

    }
  });
}
}
